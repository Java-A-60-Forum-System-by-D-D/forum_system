package com.example.ForumProject.models.persistentClasses;


import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseEntity implements GrantedAuthority {



    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRoleEnum role;

    public UserRole(UserRoleEnum role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role.toString();
    }
}
