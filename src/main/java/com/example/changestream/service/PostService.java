package com.example.changestream.service;

import com.example.changestream.domain.post.Comment;
import com.example.changestream.domain.post.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> save(Post post);

    Flux<Post> findAll();

    Mono<Post> addComment(Comment comment, String postId);

    Mono<Post> find(String postId);

    Flux<Post> subscribe(String postId);
}
