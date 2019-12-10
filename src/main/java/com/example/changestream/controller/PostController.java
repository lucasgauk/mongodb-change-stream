package com.example.changestream.controller;

import com.example.changestream.domain.Comment;
import com.example.changestream.domain.CommentRequest;
import com.example.changestream.domain.Post;
import com.example.changestream.domain.PostRequest;
import com.example.changestream.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> findAll() {
        return this.postService.findAll();
    }

    @GetMapping("/{id}")
    public Post find(@PathVariable String id) {
        return this.postService.find(id);
    }

    @GetMapping("/{id}/subscribe")
    public Flux<Post> subscribe(@PathVariable String id) {
        return this.postService.subscribe(id);
    }

    @PostMapping(value = "/{id}/comment", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Post comment(@RequestBody CommentRequest request, @PathVariable String id) {
        return this.postService.addComment(Comment.from(request), id);
    }

    @PostMapping("/create")
    public Post create(@RequestBody PostRequest postRequest) {
        return this.postService.save(Post.from(postRequest));
    }


}
