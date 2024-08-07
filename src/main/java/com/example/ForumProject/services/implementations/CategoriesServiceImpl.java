package com.example.ForumProject.services.implementations;

import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.repositories.contracts.CategoriesRepository;
import com.example.ForumProject.services.contracts.CategoriesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoriesRepository.getAllCategories();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoriesRepository.getCategoryById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoriesRepository.getCategoryByName(name);
    }
}
