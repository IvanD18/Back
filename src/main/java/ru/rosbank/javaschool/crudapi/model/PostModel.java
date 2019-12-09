package ru.rosbank.javaschool.crudapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;

@Data

public class PostModel {
  private int id;
  private String content;
  private String media;
  private boolean removed;
  private String view;
  private int likes;

  public int getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String getMedia() {
    return media;
  }

  public boolean isRemoved() {
    return removed;
  }

  public void setView(String view) {
    this.view = view;
  }



  public int getLikes() {
    return likes;
  }

  public PostModel(int id, String content, String media, boolean removed,String view, int likes) {
    this.view=view;
    this.id = id;
    this.content = content;
    this.media = media;
    this.removed = removed;
    this.likes = likes;
  }

  public PostModel(int id) {
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setMedia(String media) {
    this.media = media;
  }

  public void setRemoved(boolean removed) {
    this.removed = removed;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public static PostModel from(PostSaveRequestDto dto) {
    return new PostModel(dto.getId(), dto.getContent(), dto.getMedia(), false,"0", 0);
  }
}
