package com.example.changestream.service;

import com.example.changestream.domain.post.Comment;
import com.example.changestream.domain.post.Post;
import reactor.core.publisher.Flux;

public interface PostService {

    Post save(Post post);

    Flux<Post> findAll();

    Post addComment(Comment comment, String postId);

    Post find(String postId);

    Flux<Post> subscribe(String postId);
}
