package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.persistentClasses.Category;

import java.util.List;

public interface CategoriesRepository {

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    Category getCategoryByName(String name);


}
