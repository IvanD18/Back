package ru.rosbank.javaschool.crudapi.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.model.PostModel;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.DoubleStream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Repository
public class PostRepository {
  private final NamedParameterJdbcTemplate template;
  private static final RowMapper<PostModel> postModelRowMapper = (rs, i) -> new PostModel(
      rs.getInt("id"),
      rs.getString("content"),
      rs.getString("media"),
      rs.getBoolean("removed"),
      rs.getString("since"),
          rs.getInt("likes")
  );

  // Connection pooling
  public PostRepository(DataSource dataSource) {
    template = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<PostModel> getAll() {
    return template.query(
        "SELECT id, content, media, removed, likes FROM posts",
        postModelRowMapper);
  }

  // beauty vs speed -> for users
  public List<PostModel> getAllNoRemoved() {
    List<PostModel> all = template.query(
        "SELECT id, content, media, removed, since, likes FROM posts WHERE removed = FALSE ORDER BY id DESC ",
        postModelRowMapper);
    template.update(
            "UPDATE posts SET since = since +5 ",
            Map.of("id", 1)
    );

    return all;
  }
//  public List<PostModel> getInRange(String number) {
//    List<PostModel> part = template.query(
//            "SELECT TOP "+number+" id, content, media, removed, since, likes FROM posts WHERE removed = FALSE ORDER BY id DESC ",
//            postModelRowMapper);
//
//    return part;
//  }

  public Optional<PostModel> save(PostModel model) {
    if (model.getId() == 0) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource params = new MapSqlParameterSource();
      params.addValue("content", model.getContent());
      params.addValue("media", model.getMedia());
      template.update(
          "INSERT INTO posts (content, media) VALUES(:content, :media)",
          params,
          keyHolder
      );
      int id = keyHolder.getKey().intValue();
      model.setId(id);
      return Optional.of(model);
    }
    // id - valid
    // id - invalid
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", model.getId());
    params.addValue("content", model.getContent());
    params.addValue("media", model.getMedia());
    template.update(
        "UPDATE posts SET content = :content, media = :media WHERE id = :id",
        params
    );
    return getById(model.getId());
  }

  public Optional<PostModel> likeById(int id) {
    template.update(
        "UPDATE posts SET likes = likes + 1 WHERE id = :id",
        Map.of("id", id)
    );
    return getById(id);
  }

  public Optional<PostModel> dislikeById(int id) {
    template.update(
        "UPDATE posts SET likes = likes - 1 WHERE id = :id",
        Map.of("id", id)
    );
    return getById(id);
  }

  public Optional<PostModel> getById(int id) {
    return template.query(
        "SELECT id, content, media, removed,since, likes FROM posts WHERE id = :id LIMIT 1",
        Map.of("id", id),
        postModelRowMapper
    ).stream().findFirst();
  }

  public Optional<PostModel> getByIdNoRemoved(int id) {
    return template.query(
        "SELECT id, content, media, removed,since, likes FROM posts WHERE id = :id AND removed = FALSE LIMIT 1",
        Map.of("id", id),
        postModelRowMapper
    ).stream().findFirst();
  }

  public void removeById(int id) {
    int removed = template.update(
        "UPDATE posts SET removed = TRUE WHERE id = :id",
        Map.of("id", id)
    );
  }

  public List<PostModel> searchByContentNotRemoved(String q) {
    return template.query(
        "SELECT id, content, media, removed, likes FROM posts WHERE removed = FALSE AND content ILIKE :q",
        Map.of("q", q + "%"),
        postModelRowMapper
    );
  }

  public int countOfRow() {
    return template.update(
            "UPDATE posts SET since = 10 WHERE removed = FALSE ",
            Map.of("id", 0)
    );

  }

  public List<PostModel> sendNotification() {
    return template.query(
            "SELECT TOP 1 id, content, media, removed, since, likes FROM posts WHERE removed = FALSE ORDER BY id DESC ",
            Map.of("q", 1 + "%"),
            postModelRowMapper
    );
  }
}
