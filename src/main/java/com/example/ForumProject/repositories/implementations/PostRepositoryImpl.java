package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.exceptions.EntityDuplicateException;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.PostRepository;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Post;
import org.hibernate.Hibernate;
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
            if (filterOptionsPosts.getTagId()
                                  .isPresent()) {
                queryString.append(" left join fetch p.tags t");
            }


            filterOptionsPosts.getTitle()
                              .ifPresent(value -> {
                                  filters.add("p.title like :title");
                                  params.put("title", "%" + value + "%");
                              });

            filterOptionsPosts.getUserId()
                              .ifPresent(value -> {
                                  filters.add("p.user.id = :userId");
                                  params.put("userId", value);
                              });

            filterOptionsPosts.getContent()
                              .ifPresent(value -> {
                                  filters.add("p.content like :content");
                                  params.put("content", "%" + value + "%");
                              });

            filterOptionsPosts.getTagId()
                              .ifPresent(value -> {
                                  filters.add("t.id = :tagId");
                                  params.put("tagId", value);
                              });

            if (!filters.isEmpty()) {
                queryString.append(" where ")
                           .append(String.join(" and ", filters));
            }


            if (filterOptionsPosts.getSortBy()
                                  .isPresent()) {
                String sortBy = filterOptionsPosts.getSortBy()
                                                  .get();
                String sortOrder = filterOptionsPosts.getSortOrder()
                                                     .orElse("asc");

                String orderByClause = switch (sortBy) {
                    case "title" -> "p.title";
                    case "content" -> "p.content";
                    case "user" -> "p.user.name";
                    case "tag" -> "t.name";
                    default -> "p.id";  // default sort
                };

                queryString.append(" order by ")
                           .append(orderByClause)
                           .append(" ")
                           .append(sortOrder);
            }

            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            params.forEach(query::setParameter);

            return query.list();

        }
    }


    @Override
    public List<Post> get10MostCommentedPosts() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT p " +
                    "FROM Post p " +
                    "LEFT JOIN FETCH p.comments " +
                    "LEFT JOIN FETCH p.user " +
                    "LEFT JOIN FETCH p.tags " +
                    "GROUP BY p " +
                    "ORDER BY SIZE(p.comments) DESC";

            Query<Post> query = session.createQuery(hql, Post.class);
            query.setMaxResults(10);

            List<Post> posts = query.list();
            for (Post post : posts) {
                Hibernate.initialize(post.getComments());
                Hibernate.initialize(post.getTags());
                Hibernate.initialize(post.getUser());
            }

            return posts;

        }
    }

    @Override
    public List<Post> get10MostRecentlyAddedPosts() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT p " +
                    "FROM Post p " +
                    "LEFT JOIN FETCH p.comments " +
                    "LEFT JOIN FETCH p.user " +
                    "LEFT JOIN FETCH p.tags " +
                    "ORDER BY p.id DESC";

            Query<Post> query = session.createQuery(hql, Post.class);
            query.setMaxResults(10);

            return query.getResultList();
        }
    }





    @Override
    public void checkIfPostWithTitleExistsForUser(Post post, User user) {
        try (Session session = sessionFactory.openSession()) {
            String title = post.getTitle();
            Query<Post> query = session.createQuery("From Post p where p.title=:title and p.user.id=:userId", Post.class);
            query.setParameter("title", title);
            query.setParameter("userId", user.getId());
            if (!query.list()
                      .isEmpty()) {
                throw new EntityDuplicateException("Post with title {%s} already exists for this user".formatted(title));
            }
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
            Query<Post> query = session.createQuery("from Post where user.id = :id order by createdAt desc ", Post.class);
            query.setParameter("id", id);
            return query.list();
        }

    }

    @Override
    public List<Post> getPostsByTagId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("select p from Post p join p.tags t where t.id = :tagId", Post.class);
            query.setParameter("tagId", id);
            return query.list();
        }
    }




}
