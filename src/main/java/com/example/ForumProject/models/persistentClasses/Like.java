package com.example.ForumProject.models.persistentClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "likes")
@NoArgsConstructor
@Getter
@Setter
public class Like extends BaseEntity {

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like  = (Like) o;
        return id == like.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
