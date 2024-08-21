package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.models.persistentClasses.UserRole;
import com.example.ForumProject.repositories.contracts.UserRoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final SessionFactory sessionFactory;

    public UserRoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserRole getUserRole() {
        try (Session session = sessionFactory.openSession()) {
            UserRole userRole = session.get(UserRole.class, 3);
            return userRole;
        }
    }

    @Override
    public UserRole getAdminRole(){
        try(Session session = sessionFactory.openSession()){
            UserRole userRole = session.get(UserRole.class,1);
            return userRole;
        }
    }

    @Override
    public UserRole getModeratorRole() {
        try(Session session = sessionFactory.openSession()){
            UserRole userRole = session.get(UserRole.class,2);
            return userRole;
        }
    }




}
