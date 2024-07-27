package com.example.ForumProject.services.implementations;

import com.example.ForumProject.Helpers;
import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.models.persistentClasses.*;
import com.example.ForumProject.repositories.contracts.CommentRepository;
import com.example.ForumProject.utility.ValidatorHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Set;

import static com.example.ForumProject.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private ValidatorHelpers validatorHelpers;

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private FilterOptionsComments filterOptionsComments;

    @Test
    void getComments_Returns_ListOfComments() {
        Comment mockComment = createMockComment();
        when(commentRepository.getComments()).thenReturn(List.of(mockComment));
        List<Comment> comments = commentService.getComments();
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());
        assertEquals(1, comments.size());
        verify(commentRepository).getComments();
    }

    @Test
    void getCommentsByUser_ShouldReturns_ListOfCommentsByUser() {
        Comment mockComment = createMockComment();
        mockComment.setUser(createMockUser());
        when(commentRepository.getCommentsByUser(mockComment.getUser(), filterOptionsComments)).thenReturn(List.of(mockComment));
        List<Comment> comments = commentService.getCommentsByUser(mockComment.getUser(), filterOptionsComments);
        assertNotNull(comments);
        assertTrue(!comments.isEmpty());
        assertEquals(1, comments.size());
        verify(commentRepository).getCommentsByUser(mockComment.getUser(), filterOptionsComments);

    }


    @Test
    void getCommentById_Returns_Comment() {
        Comment mockComment = createMockComment();
        Post mockPost = createMockPost();
        when(commentRepository.getCommentById(mockComment.getId())).thenReturn(mockComment);
        mockPost.setComments(Set.of(mockComment));
        Comment comment = commentService.getCommentById(1, mockPost);
        assertNotNull(comment);
        assertEquals(1, comment.getId());
        verify(commentRepository).getCommentById(mockComment.getId());
    }

    @Test
    void getCommentById_Throws_When_CommentDoesNotExist() {
        Comment mockComment = createMockComment();
        Post mockPost = createMockPost();
        when(commentRepository.getCommentById(mockComment.getId())).thenThrow(EntityNotFoundException.class);
        mockPost.setComments(Set.of());

        assertThrows(EntityNotFoundException.class, () -> commentService.getCommentById(mockComment.getId(), mockPost));
    }

    @Test
    void createComment_ShouldCreateComment() {
        Comment mockComment = createMockComment();
        Post mockPost = createMockPost();
        when(commentRepository.createComment(mockComment)).thenReturn(mockComment);

        Comment createdComment = commentService.createComment(mockComment, mockPost);

        assertNotNull(createdComment);
        assertEquals(1, createdComment.getId());
        verify(commentRepository).createComment(mockComment);
    }

    @Test
    void updateComment_ShouldUpdateComment() {
        Comment mockComment = createMockComment();
        User mockUser = createMockUser();
        mockStatic(ValidatorHelpers.class);
        doNothing().when(ValidatorHelpers.class);
        ValidatorHelpers.roleAuthenticationValidator(any(User.class), any(UserRole.class), any(Comment.class), anyString());
        when(commentRepository.updateComment(mockComment)).thenReturn(mockComment);

        Comment updatedComment = commentService.updateComment(mockComment, mockUser);

        assertNotNull(updatedComment);
        assertEquals(1, updatedComment.getId());
        verify(commentRepository).updateComment(mockComment);
    }

    @Test
    void updateComment_Throws_AuthorizationException_When_IsNotAdminAndAuthor() {
        Comment existingComment = createMockComment();
        User author = createMockUser();
        author.setId(1);
        existingComment.setUser(author);

        User notAuthorUser = createMockUser();
        notAuthorUser.setId(2);

        Comment updatedComment = createMockComment();

        try (MockedStatic<ValidatorHelpers> mockedValidatorHelpers = mockStatic(ValidatorHelpers.class)) {
            mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(notAuthorUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            )).thenThrow(new AuthorizationException("You don't have rights to update this comment"));

            AuthorizationException thrown = assertThrows(AuthorizationException.class, () -> {
                commentService.updateComment(updatedComment, notAuthorUser);
            });

            assertEquals("You don't have rights to update this comment", thrown.getMessage());

            mockedValidatorHelpers.verify(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(notAuthorUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            ));
        }
    }

    @Test
    void updateComment_should_succeed_whenUserIsAdmin() {
        var existingComment = createMockComment();
        var authorUser = createMockUser();
        authorUser.setId(1);
        existingComment.setUser(authorUser);
        User adminUser = createMockAdmin();

        Comment updatedComment = createMockComment();


        try (MockedStatic<ValidatorHelpers> mockedValidatorHelpers = mockStatic(ValidatorHelpers.class)) {
            mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(adminUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            )).thenAnswer(invocation -> null);

            Comment result = commentService.updateComment(updatedComment, adminUser);

            verify(commentRepository).updateComment(updatedComment);

            mockedValidatorHelpers.verify(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(adminUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            ));
        }
    }

    @Test
    void deleteComment_should_throw_whenUserIsNotAdminAndNotAuthor() {
        Comment existingComment = createMockComment();
        User authorUser = createMockUser();
        authorUser.setId(1);
        existingComment.setUser(authorUser);

        User nonAdminUser = createMockUser();
        nonAdminUser.setId(2);

        when(commentRepository.getCommentById(existingComment.getId())).thenReturn(existingComment);

        try (MockedStatic<ValidatorHelpers> mockedValidatorHelpers = Mockito.mockStatic(ValidatorHelpers.class)) {
            mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(nonAdminUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            )).thenThrow(new AuthorizationException("You don't have rights to delete this comment"));

            AuthorizationException thrown = assertThrows(AuthorizationException.class, () -> {
                commentService.deleteComment(existingComment.getId(), nonAdminUser);
            });

            assertEquals("You don't have rights to delete this comment", thrown.getMessage());

            mockedValidatorHelpers.verify(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(nonAdminUser),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            ));
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deleteComment_should_succeed_whenUserIsAdmin() {
        Comment existingComment = createMockComment();
        User adminUser = createMockUser();
        adminUser.setId(1);
        adminUser.setUserRole(Set.of(new UserRole(UserRoleEnum.ADMIN)));
        existingComment.setUser(adminUser);
        when(commentRepository.getCommentById(existingComment.getId())).thenReturn(existingComment);

        try (MockedStatic<ValidatorHelpers> mockedValidatorHelpers = Mockito.mockStatic(ValidatorHelpers.class)) {
            mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                    any(User.class),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    any(Comment.class),
                    anyString()
            )).thenAnswer(invocation -> null);

            commentService.deleteComment(existingComment.getId(), adminUser);
            verify(commentRepository).deleteComment(existingComment.getId());
        }
    }

    @Test
    void deleteComment_should_succeed_whenUserIsAuthor() {
        Comment existingComment = createMockComment();
        User authorUser = createMockUser();
        authorUser.setId(1);
        existingComment.setUser(authorUser);

        User author = createMockUser();
        author.setId(1);

        try (MockedStatic<ValidatorHelpers> mockedValidatorHelpers = mockStatic(ValidatorHelpers.class)) {
            mockedValidatorHelpers.when(() -> ValidatorHelpers.roleAuthenticationValidator(
                    eq(author),
                    eq(new UserRole(UserRoleEnum.ADMIN)),
                    eq(existingComment),
                    anyString()
            )).thenAnswer(invocation -> null);
            doNothing().when(commentRepository).deleteComment(existingComment.getId());
            commentService.deleteComment(existingComment.getId(), author);
            verify(commentRepository, times(1)).deleteComment(existingComment.getId());
        }
    }
}