package com.example.ForumProject.controllers.MVC;


import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.services.contracts.CategoriesService;
import com.example.ForumProject.services.contracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoriesService categoryService;


    @Autowired
    public CategoriesController(CategoriesService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "Categories";
    }
    @GetMapping("/{id}")
    public String getCategoryByName(Model model,@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        List<Post> posts = category.getPost();
        model.addAttribute("posts", posts);
        return "Category";
    }
}