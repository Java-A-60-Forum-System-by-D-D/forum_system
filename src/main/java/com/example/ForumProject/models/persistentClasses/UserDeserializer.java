package com.example.ForumProject.models.persistentClasses;

import com.example.ForumProject.models.persistentClasses.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String username = node.get("username").asText();
        String email = node.get("email").asText();
        String photoURL = node.get("photoURL").asText();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String phoneNumber = node.get("phoneNumber").asText();
        boolean isBlocked = node.get("isBlocked").asBoolean();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhotoURL(photoURL);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setBlocked(isBlocked);

        return user;
    }
}