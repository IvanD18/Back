package ru.rosbank.javaschool.crudapi.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.model.PostModel;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;

import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PostServiceTest {

    @Test
    void save() {
        final PostModel model = new PostModel(0, "content", null, false, "0", 0);
        PostRepository repository = mock(PostRepository.class);
        final PostService service = new PostService(repository);
        Mockito.when(repository.save(model)).thenReturn(Optional.of(model));
        assertNotNull(service.save(PostSaveRequestDto.from(model)));
    }


    @Test
    void searchByContent() {
        PostRepository repository = mock(PostRepository.class);
        final PostService service = new PostService(repository);
        Mockito.when(repository.searchByContentNotRemoved("qwert1234")).thenReturn(emptyList());
        assertEquals(emptyList(), service.searchByContent("qwert1234"));
    }

    @Test
    void likeById() {
        final PostModel model = new PostModel(0, "content", null, false, "0", 0);
        PostRepository repository = mock(PostRepository.class);
        final PostService service = new PostService(repository);
        Mockito.when(repository.likeById(1)).thenReturn(Optional.of(model));
        assertNotNull(service.likeById(1));
    }

    @Test
    void dislikeById() {
        final PostModel model = new PostModel(0, "content", null, false, "0", 0);
        PostRepository repository = mock(PostRepository.class);
        final PostService service = new PostService(repository);
        Mockito.when(repository.dislikeById(1)).thenReturn(Optional.of(model));
        assertNotNull(service.dislikeById(1));
    }

    @Test
    void countOfRow() {
        PostRepository repository = mock(PostRepository.class);
        final PostService service = new PostService(repository);
        Mockito.when(repository.countOfRow()).thenReturn(12);
        assertEquals(12, service.countOfRow());
    }

}