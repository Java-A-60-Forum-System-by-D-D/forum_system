package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.dto.CommentDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.CommentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final SessionFactory sessionFactory;

    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> getComments() {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery("from Comment ", Comment.class);
            return query.list();
        }
    }

    @Override
    public List<Comment> getCommentsByUser(User user, FilterOptionsComments filterOptionsComments) {
        try (Session session = sessionFactory.openSession()) {
            /*TODO implement filtering and soroting*/
            String hql = "FROM Comment c WHERE c.user.id = :userId";
            Query<Comment> query = session.createQuery(hql, Comment.class);
            query.setParameter("userId", user.getId());
            return query.getResultList();
        }


    }

    @Override
    public Comment getCommentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery("from Comment where id = :id", Comment.class);
            query.setParameter("id", id);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Comment", id);
            }
            return query.list()
                        .get(0);
        }
    }

    @Override
    public Comment createComment(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());
            session.persist(comment);
            session.getTransaction()
                   .commit();
            return comment;
        }

    }

    @Override
    public Comment updateComment(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            comment.setUpdatedAt(LocalDateTime.now());
            session.merge(comment);
            session.getTransaction()
                   .commit();
            return comment;
        }

    }

    @Override
    public void deleteComment(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery("from Comment where id = :id", Comment.class);
            query.setParameter("id", id);
            if (query.list()
                     .isEmpty()) {
                throw new EntityNotFoundException("Comment", id);
            }
            Comment comment = query.list()
                                   .get(0);
            session.beginTransaction();
            session.remove(comment);
            session.getTransaction()
                   .commit();

        }

    }
}
