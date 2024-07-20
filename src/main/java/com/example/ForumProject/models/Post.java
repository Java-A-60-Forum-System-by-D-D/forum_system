package com.example.ForumProject.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Posts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity {


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
    @OneToMany(mappedBy = "post")
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



}
