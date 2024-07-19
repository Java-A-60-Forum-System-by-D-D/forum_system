package com.example.ForumProject.repositories;

import com.example.ForumProject.Services.UserService;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final UserService userService;
    private final SessionFactory sessionFactory;


    public PostRepositoryImpl(UserService userService, SessionFactory sessionFactory) {
        this.userService = userService;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getPosts() {
        try(Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post", Post.class);
            return query.list();
        }
    }

    @Override
    public Post getPostById(int id) {
        try(Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where id = :id", Post.class);
            query.setParameter("id", id);
            if(query.list().isEmpty()){
                throw new EntityNotFoundException("Post", id);
            }
            return query.list().get(0);
        }
    }

    @Override
    public Post updatePost(Post post) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            post.setUpdatedAt(LocalDateTime.now());
            session.getTransaction().commit();
            return post;
        }

    }

    @Override
    public Post createPost(Post post) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(post);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            session.getTransaction().commit();
            return post;
        }
    }

    @Override
    public void deletePost(int id) {
        try(Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("delete from Post where id = :id", Post.class);
            query.setParameter("id", id);
            if(query.list().isEmpty()){
                throw new EntityNotFoundException("Post", id);
            }
            Post post = query.list().get(0);
            session.beginTransaction();
            session.remove(post);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<Post> getPostsByUser(int id) {
        try(Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where user.id = :id", Post.class);
            query.setParameter("id", id);
            return query.list();
        }

    }
}
