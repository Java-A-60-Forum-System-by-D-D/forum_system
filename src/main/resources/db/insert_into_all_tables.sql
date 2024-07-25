use forum_system;
-- Insert data into roles table
INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('MODERATOR');
INSERT INTO roles (role) VALUES ('USER');

-- Insert data into tags table
INSERT INTO tags (name) VALUES ('Java');
INSERT INTO tags (name) VALUES ('Spring');
INSERT INTO tags (name) VALUES ('Database');

-- Insert data into users table
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'admin@example.com', 'Admin', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'adminuser');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'mod@example.com', 'Moderator', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'moduser');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'user@example.com', 'Regular', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser');

-- Insert data into posts table
INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (10, 1, NOW(), NOW(), 'This is a post about Java', 'Java Post');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (5, 2, NOW(), NOW(), 'This is a post about Spring', 'Spring Post');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (2, 3, NOW(), NOW(), 'This is a post about Databases', 'Database Post');

-- Insert data into comments table
INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 3, NOW(), NOW(), 'This is a comment on the Java post');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (1, 1, 2, NOW(), NOW(), 'This is a reply to the first comment on the Java post');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 1, NOW(), NOW(), 'This is a comment on the Spring post');

-- Insert data into likes table
INSERT INTO likes (post_id, user_id, created_at)
VALUES (1, 2, NOW());

INSERT INTO likes (post_id, user_id, created_at)
VALUES (1, 3, NOW());

INSERT INTO likes (post_id, user_id, created_at)
VALUES (2, 1, NOW());

-- Insert data into post_tags table
INSERT INTO post_tags (id, tag_id)
VALUES (1, 1);

INSERT INTO post_tags (id, tag_id)
VALUES (2, 2);

INSERT INTO post_tags (id, tag_id)
VALUES (3, 3);

-- Insert data into users_roles table
INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1);

INSERT INTO users_roles (role_id, user_id)
VALUES (2, 2);

INSERT INTO users_roles (role_id, user_id)
VALUES (3, 3);