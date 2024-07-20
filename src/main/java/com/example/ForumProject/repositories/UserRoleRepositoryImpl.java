package com.example.ForumProject.repositories;

import com.example.ForumProject.models.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryImpl {
    private final SessionFactory sessionFactory;

    public UserRoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserRole getUserRole() {
        try (Session session = sessionFactory.openSession()) {
            UserRole userRole = session.get(UserRole.class, 3);
            return userRole;
        }
    }


}
