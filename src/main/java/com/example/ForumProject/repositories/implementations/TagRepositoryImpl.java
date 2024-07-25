package com.example.ForumProject.repositories.implementations;

import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.repositories.contracts.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;

    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag where name = :name", Tag.class);
            query.setParameter("name", name);
            if(query.getResultList().isEmpty()){
                return Optional.empty();
            }
            return Optional.ofNullable(query.list().get(0));
        }
    }

    @Override
    public Tag createTag(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(tag);
            session.getTransaction().commit();
            return tag;
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Tag> findTagsByPostId(int postId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Post p JOIN p.tags t WHERE p.id = :postId";
            Query<Tag> query = session.createQuery(hql, Tag.class);
            query.setParameter("postId", postId);
            return query.getResultList();
        }
    }

    @Override
    public List<Tag> findAllTags() {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag", Tag.class);
            return query.list();
        }
    }

    @Override
    public Tag findTagById(int tagId) {
        try (Session session = sessionFactory.openSession()) {
            Tag tag = session.get(Tag.class, tagId);
            if (tag == null) {
                throw new EntityNotFoundException("Tag", tagId);
            }
            return tag;
        }
    }

    @Override
    public Tag createOrUpdateTag(Tag tag) {
        if (findByName(tag.getName()).isPresent()) {
            return tag;
        }
        return createTag(tag);
    }

}
