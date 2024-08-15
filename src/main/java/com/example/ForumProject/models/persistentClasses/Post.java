package com.example.ForumProject.models.persistentClasses;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Posts")
@Getter
@Setter
public class Post extends BaseEntity {

    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    @NotNull(message = "Field cannot be null")
    @Size(min = 16, max = 64, message = "Title must be between 16 and 64")
    private String title;

    @Column(name = "content")
    @NotNull(message = "Field cannot be null")
    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192")
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private Set<Like> likes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public Post() {
    }

    public int getLikesCount() {
        return this.likes.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



