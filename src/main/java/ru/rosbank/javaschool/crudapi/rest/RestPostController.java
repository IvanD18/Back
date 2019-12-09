package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.model.PostModel;
import ru.rosbank.javaschool.crudapi.service.PostService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController // ко всем методам будет дописано @ResponseBody
@RequestMapping("/api/posts")
public class RestPostController {

    private int num = 12;
    private int count;
    private final PostService service;
    private final Logger logger = LoggerFactory.getLogger(RestPostController.class);
    private int countOfRow;
    private int notification;


    @GetMapping
    public List<PostResponseDto> getAllInRange() {
        countOfRow = service.countOfRow();
        logger.info(Thread.currentThread().getName());
        if (count == 0) {
            return service.getAllInRange(5);
        }
        if (count > countOfRow) {
            return service.getAllInRange(countOfRow);
        }
        return service.getAllInRange(count);
    }

    @GetMapping("/poling")
    public List<PostModel> sendNotification() {
        int tmp = service.sendNotification().get(0).getId();
        if (tmp > num) {
            num =tmp;
            System.out.println("New Post Added");
            return service.sendNotification();
        }
        if (tmp < num) {
            num =tmp;
            System.out.println("Post Deleted");
        }

        System.out.println( Collections.emptyList());
        return Collections.emptyList();
    }


    @GetMapping("/count/{count}") // @RequestMapping(method = GET) -> GET /api/posts
    public void getAllInRange(@PathVariable int count) {
        this.count = count;
    }

    // ТТП
    @GetMapping(params = "q") // фильтрация по наличию параметра
    public List<PostResponseDto> searchByContent(@RequestParam String q) {
        return service.searchByContent(q);
    }

    // -> x-www-urlencoded...
    // -> multipart/form-data
    // Content-Type: MIME тип
    // POST -> create/update
    @PostMapping // DataBinding
    public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
        return service.save(dto);
    }

    // DELETE /api/posts/:id -> ?itemId=10 -> req.getParameter()
    @DeleteMapping("/{id}")
// public void removeById(@PathVariable("id") int id)
// if param name = path variable name, то дополнительно ничего не нужно
    public void removeById(@PathVariable int id) {
//    throw new BadRequestException("bad.request");
        service.removeById(id);
    }

    @PostMapping("/{id}/likes")
    public PostResponseDto likeById(@PathVariable int id) {
        return service.likeById(id);
    }

    @DeleteMapping("/{id}/likes")
    public PostResponseDto dislikeById(@PathVariable int id) {
        return service.dislikeById(id);
    }
}
