package com.example.ForumProject.repositories;


import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User where username = :username", User.class);
            query.setParameter("username", username);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return query.list()
                        .get(0);

        }
    }

    @Override
    public User getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("From User where firstName = :name", User.class);
            query.setParameter("name", firstName);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("User", "first_name", firstName);
            }
            return query.list()
                        .get(0);
        }
    }

    @Override
    public List<User> getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email=:email", User.class);
            query.setParameter("email", email);
            return query.list();
        }
    }

    @Override
    public User updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            user.setUpdatedAt(LocalDateTime.now());
            session.getTransaction()
                   .commit();
            return user;
        }
    }


    @Override
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(user);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            session.getTransaction()
                   .commit();
        }
    }


}



