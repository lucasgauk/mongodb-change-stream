package com.example.changestream.domain.post;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String content;
    private LocalDateTime createdAt;

    public static Comment from(CommentRequest request) {
        return new Comment(request.getContent(), LocalDateTime.now());
    }

}
