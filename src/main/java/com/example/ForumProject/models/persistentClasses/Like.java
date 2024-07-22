package com.example.ForumProject.models.persistentClasses;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Like extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
    }
    public Like(int id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }


}
