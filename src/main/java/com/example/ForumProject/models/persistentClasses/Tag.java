package com.example.ForumProject.models.persistentClasses;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class Tag extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id==tag.id;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
