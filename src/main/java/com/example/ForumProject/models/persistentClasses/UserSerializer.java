package com.example.ForumProject.models.persistentClasses;
import com.example.ForumProject.models.persistentClasses.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("username", user.getUsername());
        gen.writeStringField("email", user.getEmail());
        gen.writeStringField("photoURL", user.getPhotoURL());
        gen.writeStringField("firstName", user.getFirstName());
        gen.writeStringField("lastName", user.getLastName());
        gen.writeStringField("phoneNumber", user.getPhoneNumber());
        gen.writeBooleanField("isBlocked", user.isBlocked());
        gen.writeEndObject();
    }
}