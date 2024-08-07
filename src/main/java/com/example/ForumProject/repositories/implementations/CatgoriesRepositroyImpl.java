package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.repositories.contracts.CategoriesRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatgoriesRepositroyImpl implements CategoriesRepository {

    private final SessionFactory sessionFactory;


    @Autowired
    public CatgoriesRepositroyImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            Query<Category> query = session.createQuery("From Category", Category.class);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Categories", "emtpty");
            }
            return query.list();
        }
    }

    @Override
    public Category getCategoryById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public Category getCategoryByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Category> query = session.createQuery("From Category where categoryName = :name", Category.class);
            query.setParameter("name", name);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Categories", "emtpty");
            }
            return query.list().get(0);
        }
    }
}
