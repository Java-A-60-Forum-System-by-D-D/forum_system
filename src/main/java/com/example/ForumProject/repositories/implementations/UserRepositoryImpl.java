package com.example.ForumProject.repositories.implementations;


import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsers;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRoleEnum;
import com.example.ForumProject.repositories.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getUsersPosts(User user, FilterOptionsUsersPosts filterOptionsUsersPosts) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            StringBuilder queryString = new StringBuilder("select distinct p from Post p");


            queryString.append(" left join fetch p.user");
            if (filterOptionsUsersPosts.getTagId().isPresent()) {
                queryString.append(" left join fetch p.tags t");
            }

            filters.add("p.user = :user");
            params.put("user", user);

            filterOptionsUsersPosts.getTitle().ifPresent(value -> {
                filters.add("p.title like :title");
                params.put("title", "%" + value + "%");
            });


            filterOptionsUsersPosts.getContent().ifPresent(value -> {
                filters.add("p.content like :content");
                params.put("content", "%" + value + "%");
            });

            filterOptionsUsersPosts.getTagId().ifPresent(value -> {
                filters.add("t.id = :tagId");
                params.put("tagId", value);
            });

            if (!filters.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filters));
            }


            if (filterOptionsUsersPosts.getSortBy().isPresent()) {
                String sortBy = filterOptionsUsersPosts.getSortBy().get();
                String sortOrder = filterOptionsUsersPosts.getSortOrder().orElse("asc");

                String orderByClause = switch (sortBy) {
                    case "title" -> "p.title";
                    case "content" -> "p.content";
                    case "tag" -> "t.name";
                    default -> "p.id";  // default sort
                };

                queryString.append(" order by ").append(orderByClause).append(" ").append(sortOrder);
            }

            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            params.forEach(query::setParameter);

            return query.list();
        }
    }

    @Override
    public List<User> getUsers(FilterOptionsUsers filterOptionsUsers) {
        try (Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            StringBuilder queryString = new StringBuilder("select distinct u from User u");

            if (filterOptionsUsers.getUsername().isPresent()) {
                filters.add("u.username like :username");
                params.put("username", "%" + filterOptionsUsers.getUsername().get() + "%");
            }

            if (filterOptionsUsers.getFirstName().isPresent()) {
                filters.add("u.firstName like :firstName");
                params.put("firstName", "%" + filterOptionsUsers.getFirstName().get() + "%");
            }

            if (filterOptionsUsers.getLastName().isPresent()) {
                filters.add("u.lastName like :lastName");
                params.put("lastName", "%" + filterOptionsUsers.getLastName().get() + "%");
            }

            if (filterOptionsUsers.getRole().isPresent()) {
                queryString.append(" join u.roles r");
                filters.add("r.role = :role");
                params.put("role", UserRoleEnum.fromString(filterOptionsUsers.getRole().get()));
            }

            if (!filters.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filters));
            }

            Query<User> query = session.createQuery(queryString.toString(), User.class);
            params.forEach(query::setParameter);

            return query.list();
        }
    }

    @Override
    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User u left join   Post p on  p.user = u ", User.class);
            return query.list();
        }
    }

    @Override
    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public List<User> getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User where username = :username", User.class);
            query.setParameter("username", username);
            return query.list();

        }
    }

    @Override
    public User getUserByFirstName(String firstName) {
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
    public List<User> getUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email=:email", User.class);
            query.setParameter("email", email);
            return query.list();
        }
    }

    @Override
    public User createUser(User user) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(user);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            session.getTransaction()
                    .commit();
            return user;
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


}



