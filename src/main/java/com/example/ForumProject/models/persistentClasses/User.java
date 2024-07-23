package com.example.ForumProject.models.persistentClasses;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {


    public User(String username, String password, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.userRole = userRoles;
    }

    @Column(name = "username")
    @NotNull(message = "Field cannot be null")
    private String username;

    @Column(name = "email")
    @NotNull(message = "Field cannot be null")
    private String email;

    @Column(name = "photo_url")
    private String photoURL;

    @JsonIgnore
    @Column(name = "password_hash")
    @NotNull(message = "Field cannot be null")
    private String password;

    @Column(name = "first_name")
    @NotNull(message = "Field cannot be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Field cannot be null")
    private String lastName;

    @JsonIgnore
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_Id")
    )
    private Set<UserRole> userRole;


    @JsonIgnore
    @Column(name = "is_blocked")
    private boolean isBlocked;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Comment> comments;

    @JsonIgnore
//    @JsonManagedReference /*TODO Read about it*/
//    @JsonBackReference/*TODO Read about it*/
    @OneToMany(mappedBy = "user")
    private Set<Like> likes;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserRole();
    }


    public User() {
        this.posts = new HashSet<>();
        this.comments = new HashSet<>();
        this.likes = new HashSet<>();
        this.userRole = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
