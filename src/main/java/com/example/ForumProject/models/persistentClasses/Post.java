package com.example.ForumProject.models.persistentClasses;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"user", "comments", "likes"})
public class Post extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    @NotNull(message = "Field cannot be null")
    private String title;

    @Column(name = "content")
    @NotNull(message = "Field cannot be null")
    private String content;


    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private Set<Like> likes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Post() {
        this.comments = new HashSet<>();
        this.likes = new HashSet<>();
        this.tags = new HashSet<>();
    }

    public int getLikesCount() {
        return this.likes.size();
    }
}
