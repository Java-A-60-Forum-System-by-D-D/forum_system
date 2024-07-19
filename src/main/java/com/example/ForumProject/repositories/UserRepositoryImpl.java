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
public class UserRepositoryImpl {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

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

    public List<User> getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email=:email", User.class);
            query.setParameter("email", email);
            return query.list();
        }
    }


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


//public List<User> searchByFirstName(String firstName) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<User> query = session.createQuery("From User where first_name like :name", User.class);
//            query.setParameter("first_name", "%" + firstName + "%");
//            if (query.list()
//                     .isEmpty()) {
//                throw new EntityNotFoundException("Users", "first name", firstName);
//            }
//            return query.list();
//
//        }
//    }
//
//    public List<User> searchByName(String username) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<User> query = session.createQuery("FROM User where username like :username", User.class);
//            query.setParameter("username", "%" + username + "%");
//            if (query.list()
//                     .isEmpty()) {
//                throw new EntityNotFoundException("User", "username", username);
//            }
//            return query.list();
//
//        }
//    }
