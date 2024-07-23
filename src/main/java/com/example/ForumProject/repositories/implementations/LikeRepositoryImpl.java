package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.repositories.contracts.LikeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class LikeRepositoryImpl implements LikeRepository {
    private final SessionFactory sessionFactory;

    public LikeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Like save(Like like) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            like.setCreatedAt(LocalDateTime.now());
            session.persist(like);
            session.getTransaction().commit();
            return like;
        }

    }

    public void delete(Like like) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(like);
            session.getTransaction().commit();
        }
    }

    public Optional<Like> findByPostIdAndUserId(int postId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Like> query = session.createQuery("FROM Like WHERE post.id = :postId AND user.id = :userId", Like.class);
            query.setParameter("postId", postId);
            query.setParameter("userId", userId);
            Like like = query.uniqueResult();
            return Optional.ofNullable(like);
        }
    }

    public List<Like> findByPostId(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Like> query = session.createQuery("FROM Like WHERE post.id = :postId", Like.class);
            query.setParameter("postId", postId);
            return query.list();
        }
    }

    public int countByPostId(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Integer> query = session.createQuery("SELECT SIZE(p.likes) FROM Post p WHERE p.id = :postId", Integer.class);
            query.setParameter("postId", postId);
            return query.uniqueResult();
        }
    }

}
