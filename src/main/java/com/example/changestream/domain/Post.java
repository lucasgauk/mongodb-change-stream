package com.example.changestream.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@TypeAlias("Post")
@NoArgsConstructor
public class Post {

    @Id
    private ObjectId id;
    private String content;
    private List<Comment> comments;
    @CreatedDate
    private LocalDateTime createdAt;

    public Post(String content) {
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public static Post from(PostRequest request) {
        return new Post(request.getContent());
    }

}
