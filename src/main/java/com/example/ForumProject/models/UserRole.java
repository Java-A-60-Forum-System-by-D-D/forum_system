package com.example.ForumProject.models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseEntity implements GrantedAuthority {



    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRoleEnum role;

    @Override
    public String getAuthority() {
        return this.role.toString();
    }
}
