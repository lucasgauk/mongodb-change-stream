package com.example.changestream.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.example.changestream.domain.Comment;
import com.example.changestream.domain.Post;
import com.example.changestream.domain.PostRepository;
import com.example.changestream.service.PostService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final ReactiveMongoOperations reactiveTemplate;

    public PostServiceImpl(PostRepository repository,
                           ReactiveMongoOperations reactiveTemplate) {
        this.repository = repository;
        this.reactiveTemplate = reactiveTemplate;
    }

    public Post save(Post post) {
        return this.repository.save(post).block();
    }

    public Flux<Post> findAll() {
        return this.repository.findAll();
    }

    public Post addComment(Comment comment, String postId) {
        Post post = this.find(postId);
        if (post != null) {
            post.getComments().add(comment);
            return this.save(post);
        }
        return null;
    }

    public Post find(String postId) {
        return this.repository.findById(postId).block();
    }

    public Flux<Post> subscribe(String postId) {
        Aggregation fluxAggregation = newAggregation(match(where("operationType")
                                                               .is("update")
                                                               .and("updateDescription.updatedFields._id")
                                                               .is(new ObjectId(postId))));

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
