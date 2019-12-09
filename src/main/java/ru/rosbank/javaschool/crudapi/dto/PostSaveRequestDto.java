package ru.rosbank.javaschool.crudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.crudapi.model.PostModel;

@Data

public class PostSaveRequestDto {

  private int id;
  private String content;
  private String media;

    public static PostSaveRequestDto from(PostModel model) {
      return new PostSaveRequestDto(
              model.getId(),
              model.getContent(),
              model.getMedia()
      );
    }

    public int getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String getMedia() {
    return media;
  }

  public PostSaveRequestDto() {
  }

  public PostSaveRequestDto(int id, String content, String media) {
    this.id = id;
    this.content = content;
    this.media = media;
  }
}
