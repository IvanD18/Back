package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.exception.NotFoundException;
import ru.rosbank.javaschool.crudapi.model.PostModel;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
  private final PostRepository repository;
  private final Logger logger = LoggerFactory.getLogger(PostService.class);

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<PostResponseDto> getAll() {
    return repository.getAllNoRemoved().stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }
  public List<PostResponseDto> getAllInRange(int count) {
    return repository.getAllNoRemoved().stream()
            .map(PostResponseDto::from)
            .collect(Collectors.toList()).subList(0,count);
  }

  // update -> ...
  // select -> ...
  public PostResponseDto save(PostSaveRequestDto dto) {
    logger.info(dto.toString());
    return repository.save(PostModel.from(dto))
        .map(PostResponseDto::from)
        .orElseThrow(BadRequestException::new);
  }

  public void removeById(int id) {
    repository.removeById(id);
  }

  public List<PostResponseDto> searchByContent(String q) {
    return repository.searchByContentNotRemoved(q).stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());
  }

  public PostResponseDto likeById(int id) {
    return repository.likeById(id)
        .map(PostResponseDto::from)
        .orElseThrow(BadRequestException::new);
  }

  public PostResponseDto dislikeById(int id) {
    return repository.dislikeById(id)
        .map(PostResponseDto::from)
        .orElseThrow(BadRequestException::new);
  }

  public int countOfRow() {
    return repository.countOfRow();
  }

  public List<PostModel> sendNotification() {
    return repository.sendNotification();
  }
}
