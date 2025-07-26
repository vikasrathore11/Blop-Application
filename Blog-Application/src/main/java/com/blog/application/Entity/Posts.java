package com.blog.application.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    private String id;

    private String title;

    @Column(length = 5000)
    private String content;

    private String author;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comments> comments;
}
