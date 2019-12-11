package com.example.changestream.domain.internal;

import java.time.LocalDateTime;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {

    @Id
    private ObjectId id;
    @CreatedDate
    private LocalDateTime createdAt;

}
