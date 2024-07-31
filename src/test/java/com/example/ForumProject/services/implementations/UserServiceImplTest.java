package com.example.ForumProject.services.implementations;

import com.example.ForumProject.Helpers;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.repositories.contracts.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    FilterOptionsUsersPosts filterOptionsUsersPosts;

    @Test
    public void getAllUsers_Returns_ListOfUsers(){
        User user = Helpers.createMockUser();
        User adminUser = Helpers.createMockAdmin();
        List<User> users = Arrays.asList(user, adminUser);

        when(userRepository.getUsers()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(users, result);
        verify(userRepository, times(1)).getUsers();
    }

    @Test
    void loadUserByUsername_ReturnUser_WhenUserExists() {
        User mockUser = Helpers.createMockUser();
        List<User> mockUserList = Collections.singletonList(mockUser);
        when(userRepository.getUserByUsername(mockUser.getUsername())).thenReturn(mockUserList);

        User result = userService.getUserByUsername("MockUsername");

        assertEquals(mockUser, result);
        assertEquals(mockUser.getUsername(), result.getUsername());
    }
    @Test
    void loadUserByUsername_Throws_WhenUserWithUsernameNotExists() {
        String nonExistentUsername = "NonExistentUsername";
        when(userRepository.getUserByUsername(nonExistentUsername)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,
                () -> {
            userService.getUserByUsername(nonExistentUsername);
        });
    }

    @Test
    void getUsers_ReturnsAllUsers_WhenUserExists() {
        User mockUser = Helpers.createMockUser();
        List<User> mockUserList = Collections.singletonList(mockUser);
        when(userRepository.getUsers()).thenReturn(mockUserList);
        List<User> result = userService.getUsers();
        assertEquals(mockUserList, result);

    }

    @Test
    void getUserById_ReturnsUser_WhenUserExists() {
        User mockUser = Helpers.createMockUser();

        when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        User result = userService.getUserById(1);

        assertEquals(mockUser, result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getUsername(), result.getUsername());
    }
    @Test
    void getUserById_Throws_WhenUserWithIdNotExists() {
        int nonExistentUserId = 999;
        when(userRepository.getUserById(nonExistentUserId)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(nonExistentUserId);
        });
    }
    @Test
    void getUserByFirstName_ReturnsUser_WhenUserExists() {
        User mockUser = Helpers.createMockUser();

        when(userRepository.getUserByFirstName(mockUser.getFirstName())).thenReturn(mockUser);

        User result = userService.getUserByFirstName("MockFirstName");

        assertEquals(mockUser, result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getFirstName(), result.getFirstName());
    }
    @Test
    void getUserByFirstName_Throws_WhenUserWithFirstNameNotExists() {
        String nonExistentFirstName = "NonExistentFirstName";
        when(userRepository.getUserByFirstName(nonExistentFirstName)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserByFirstName(nonExistentFirstName);
        });
    }
    @Test
    void getUserByEmail_ShouldReturnUser_WhenUserExists() {
        User mockUser = Helpers.createMockUser();
        List<User> mockUserList = Collections.singletonList(mockUser);
        when(userRepository.getUserByEmail(mockUser.getEmail())).thenReturn(mockUserList);

        User result = userService.getUserByEmail("mock@user.com");

        assertEquals(mockUser, result);
        assertEquals(mockUser.getUsername(), result.getUsername());
        assertEquals(mockUser.getEmail(), result.getEmail());
    }
    @Test
    void getUserByEmail_Throws_WhenUserWithEmailNotExists() {
        String nonExistentUsername = "non@existent.email";
        when(userRepository.getUserByEmail(nonExistentUsername)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class,
                () -> {
                    userService.getUserByEmail(nonExistentUsername);
                });
    }




    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void updateUser_ShouldUpdateUser_WhenUserExists() {

        User mockUser = Helpers.createMockUser();
        User anotherUser = Helpers.createMockUser();
        anotherUser.setId(1);
        anotherUser.setFirstName("UpdatedLastName");
        when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);
        when(userRepository.updateUser(Mockito.any(User.class))).thenReturn(anotherUser);
        User result = userService.updateUser(mockUser);

        assertEquals(anotherUser.getLastName(), result.getLastName());
        verify(userRepository, times(1)).updateUser(mockUser);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        User mockUser = Helpers.createMockUser();
        when(userRepository.updateUser(mockUser)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(mockUser);
        });
    }

    @Test
    void getPostsByUser() {
        User mockUser = Helpers.createMockUser();
        Post mockPost = Helpers.createMockPost();
        mockPost.setUser(mockUser);
        List<Post> mockPostList = Collections.singletonList(mockPost);

        when(userRepository.getUsersPosts(mockUser, filterOptionsUsersPosts)).thenReturn(mockPostList);

        List<Post> result = userService.getPostsByUser(mockUser, filterOptionsUsersPosts);

        assertEquals(mockPostList, result);
        verify(userRepository, times(1)).getUsersPosts(mockUser, filterOptionsUsersPosts);
    }
}