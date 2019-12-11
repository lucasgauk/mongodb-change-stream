package com.example.changestream.domain.post;

import com.example.changestream.domain.internal.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@TypeAlias("Post")
@NoArgsConstructor
public class Post extends BaseEntity {

    private String content;
    private List<Comment> comments;

    private Post(String content) {
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public static Post from(PostRequest request) {
        return new Post(request.getContent());
    }

}
