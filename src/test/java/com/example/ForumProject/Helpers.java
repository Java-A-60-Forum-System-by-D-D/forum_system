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

//    public static Beer createMockBeer() {
//        var mockBeer = new Beer();
//        mockBeer.setId(1);
//        mockBeer.setName("MockBeer");
//        mockBeer.setCreatedBy(createMockUser());
//        mockBeer.setStyle(createMockStyle());
//        return mockBeer;
//    }
//
//    public static Style createMockStyle() {
//        var mockStyle = new Style();
//        mockStyle.setId(1);
//        mockStyle.setName("MockStyle");
//        return mockStyle;
//    }

    public static Post createMockPost() {
        Post mockPost = new Post();
        mockPost.setUser(createMockUser());
        mockPost.setId(1);
        mockPost.setContent("x".repeat(42));
        mockPost.setTitle("x".repeat(20));
        return mockPost;
    }

//    public static Comment createMockComment(){
//        Comment mockComment = new Comment();
//        mockComment.setUser(createMockUser());
//        mockComment.setPost(createMockPost());
//    }

    public static FilterOptionsPosts createMockFilterOptions() {
        return new FilterOptionsPosts(
                "x".repeat(20),
                "x".repeat(42),
                1,
                1,
                "title",
                "asc");
    }

//    public static BeerDto createBeerDto() {
//        BeerDto dto = new BeerDto();
//        dto.setStyleId(1);
//        dto.setName("MockBeer");
//        dto.setAbv(4.5);
//        return dto;
//    }

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
