package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.Category;

import java.util.List;

public interface CategoriesService {

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    Category getCategoryByName(String name);
}
