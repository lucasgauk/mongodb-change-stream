package com.example.changestream.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.example.changestream.domain.post.Comment;
import com.example.changestream.domain.post.Post;
import com.example.changestream.domain.post.PostRepository;
import com.example.changestream.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final ReactiveMongoTemplate reactiveTemplate;

    public PostServiceImpl(PostRepository repository,
                           ReactiveMongoTemplate reactiveTemplate) {
        this.repository = repository;
        this.reactiveTemplate = reactiveTemplate;
    }

    public Mono<Post> save(Post post) {
        return this.repository.save(post);
    }

    public Flux<Post> findAll() {
        return this.repository.findAll();
    }

    public Mono<Post> addComment(Comment comment, String postId) {
        return this.find(postId).flatMap(post -> {
            post.getComments().add(comment);
            return this.save(post);
        });
    }

    public Mono<Post> find(String postId) {
        return this.repository.findById(postId);
    }

    public Flux<Post> subscribe(String postId) {
        Aggregation fluxAggregation = newAggregation(match(where("fullDocument._id").is(new ObjectId(postId))));

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                                                         .returnFullDocumentOnUpdate()
                                                         .filter(fluxAggregation)
                                                         .build();

        return reactiveTemplate.changeStream("post",
                                             options,
                                             Post.class)
                               .map(ChangeStreamEvent::getBody);
    }
}
