package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;


    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getPosts(FilterOptionsPosts filterOptionsPosts) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            StringBuilder queryString = new StringBuilder("select distinct p from Post p");


            queryString.append(" left join fetch p.user");
            if (filterOptionsPosts.getTagId().isPresent()) {
                queryString.append(" left join fetch p.tags t");
            }


            filterOptionsPosts.getTitle().ifPresent(value -> {
                filters.add("p.title like :title");
                params.put("title", "%" + value + "%");
            });

            filterOptionsPosts.getUserId().ifPresent(value -> {
                filters.add("p.user.id = :userId");
                params.put("userId", value);
            });

            filterOptionsPosts.getContent().ifPresent(value -> {
                filters.add("p.content like :content");
                params.put("content", "%" + value + "%");
            });

            filterOptionsPosts.getTagId().ifPresent(value -> {
                filters.add("t.id = :tagId");
                params.put("tagId", value);
            });

            if (!filters.isEmpty()) {
                queryString.append(" where ").append(String.join(" and ", filters));
            }


            if (filterOptionsPosts.getSortBy().isPresent()) {
                String sortBy = filterOptionsPosts.getSortBy().get();
                String sortOrder = filterOptionsPosts.getSortOrder().orElse("asc");

                String orderByClause = switch (sortBy) {
                    case "title" -> "p.title";
                    case "content" -> "p.content";
                    case "user" -> "p.user.name";
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
    public Post getPostById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where id = :id", Post.class);
            query.setParameter("id", id);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Post", id);
            }
            return query.list()
                        .get(0);
        }
    }

    @Override
    public Post updatePost(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            post.setUpdatedAt(LocalDateTime.now());
            session.getTransaction()
                   .commit();
            return post;
        }

    }

    @Override
    public Post createPost(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            session.getTransaction()
                   .commit();
            return post;
        }
    }

    @Override
    public void deletePost(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where id = :id", Post.class);
            query.setParameter("id", id);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Post", id);
            }
            Post post = query.list()
                             .get(0);
            session.beginTransaction();
            session.remove(post);
            session.getTransaction()
                   .commit();
        }

    }

    @Override
    public List<Post> getPostsByUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where user.id = :id", Post.class);
            query.setParameter("id", id);
            return query.list();
        }

    }




}
