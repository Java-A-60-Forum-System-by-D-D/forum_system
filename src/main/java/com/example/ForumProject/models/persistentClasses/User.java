package com.example.ForumProject.models.persistentClasses;



import com.example.ForumProject.utility.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@Schema(description = "User Entity")
@JsonSerialize(using = UserSerializer.class)
@JsonDeserialize(using = UserDeserializer.class)
public class User extends BaseEntity implements UserDetails, GrantedAuthority {

    public User(String username, String password, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.userRole = userRoles;
    }

    @Column(name = "username",unique = true)
    @NotNull(message = "Field cannot be null")
    @Schema(description = "Username")
    private String username;

    @Column(name = "email",unique = true)
    @NotNull(message = "Field cannot be null")
    private String email;

    @Column(name = "photo_url")
    private String photoURL;

    @JsonIgnore
    @Column(name = "password_hash")
    @Size(min =8, max = 100,message = "Password needs to be between 8 and 20 symbols")
    @NotNull(message = "Field cannot be null")
    private String password;

    @Column(name = "first_name")
    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "First name must be between 4 and 32 symbols")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Field cannot be null")
    @Size(min = 4, max = 32, message = "Last name must be between 4 and 32 symbols")
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
    private  Set<UserRole> userRole;


    @JsonIgnore
    @Column(name = "is_blocked")
    private boolean isBlocked;

    @JsonIgnore
    @Column(name = "created_at")
    private  LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private transient Set<Post> posts;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private transient Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private transient Set<Like> likes;


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
    public Set<UserRole> getRoles() {
        return userRole;
    }

    public void setRoles(Set<UserRole> roles) {
        this.userRole = roles;
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

    @Override
    public String getAuthority() {
        return this.userRole.toString();
    }

    public boolean isAdmin() {
        return this.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("Admin"));
    }
    public int getPostsCount() {
        return this.posts.size();
    }
}
