package com.example.ForumProject;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.persistentClasses.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

public class Helpers {

    public static User createMockAdmin() {
        User mockUser = createMockUser();

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(UserRoleEnum.ADMIN));
        mockUser.setUserRole(roles);

        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        return mockUser;
    }


    public static Post createMockPost() {
        Post mockPost = new Post();
        mockPost.setUser(createMockUser());
        mockPost.setId(1);
        mockPost.setContent("x".repeat(42));
        mockPost.setTitle("x".repeat(20));
        mockPost.setLikes(new HashSet<>());
        mockPost.setTags(new HashSet<>());
        return mockPost;
    }
    public static Comment createMockComment() {
        Comment mockComment = new Comment();
        mockComment.setUser(createMockUser());
        mockComment.setId(1);
        mockComment.setContent("x".repeat(42));
        mockComment.setPost(createMockPost());
        return mockComment;
    }

    public static Tag createMockTag() {
        Tag mockTag = new Tag();
        mockTag.setName("X".repeat(5));
        return mockTag;
    }


    public static FilterOptionsPosts createMockFilterOptions() {
        return new FilterOptionsPosts(
                "x".repeat(20),
                "x".repeat(42),
                1,
                1,
                "title",
                "asc");
    }


    /**
     * Accepts an object and returns the stringified object.
     * Useful when you need to pass a body to a HTTP request.
     */
    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
