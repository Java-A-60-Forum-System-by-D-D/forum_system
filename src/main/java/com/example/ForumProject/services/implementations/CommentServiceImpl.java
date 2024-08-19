package com.example.ForumProject.services.implementations;

import com.example.ForumProject.models.dto.CommentDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.services.contracts.CommentService;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.CommentRepository;
import com.example.ForumProject.services.contracts.PostService;
import com.example.ForumProject.utility.ValidatorHelpers;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    public static final String INVALID_DELETE_COMMAND = "You dont have rights to delete this comment";
    public static final String INVALID_UPDATE_COMMAND = "You dont have rights to update this comment";
    private final CommentRepository commentRepository;


    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;

    }

    @Override
    public List<Comment> getComments() {
        return commentRepository.getComments();
    }


    @Override
    public List<Comment> getCommentsByUser(User user, FilterOptionsComments filterOptionsComments) {

        return commentRepository.getCommentsByUser(user,filterOptionsComments);
    }

    @Override
    public Comment getCommentById(int id, Post post) {
        Comment comment = commentRepository.getCommentById(id);
        if (!post.getComments()
                 .contains(comment)) {
            throw new EntityNotFoundException("Comment", id);
        }


        return comment;
    }

    @Override
    public Comment createComment(Comment comment,Post post) {
        User user = comment.getUser();

        if(comment.getParentCommentId()!=null){
            getCommentById(comment.getParentCommentId(),post);
        }

        return commentRepository.createComment(comment);
    }

    @Override
    public Comment updateComment(Comment comment, User user) {

        ValidatorHelpers.roleAuthenticationValidator(user,new UserRole(UserRoleEnum.ADMIN) ,comment, INVALID_UPDATE_COMMAND);

        return commentRepository.updateComment(comment);
    }

    @Override
    public void deleteComment(int id, User user) {

        ValidatorHelpers.roleAuthenticationValidator(user,new UserRole(UserRoleEnum.ADMIN) ,commentRepository.getCommentById(id), INVALID_DELETE_COMMAND);

        commentRepository.deleteComment(id);

    }


}
