package com.example.ForumProject.models.persistentClasses;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(name = "category_name")
    @Size(min = 3, max = 30, message = "Category must be between 3 and 30 symbols")
    private String categoryName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Post> post;


    @Column(name = "description")
    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 symbols")
    private String description;

}
