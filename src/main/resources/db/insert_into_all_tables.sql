-- Insert data into roles table
INSERT INTO forum_test.roles (role) VALUES ('ADMIN');
INSERT INTO forum_test.roles (role) VALUES ('MODERATOR');
INSERT INTO forum_test.roles (role) VALUES ('USER');

-- Insert data into tags table
INSERT INTO forum_test.tags (name) VALUES ('Java');
INSERT INTO forum_test.tags (name) VALUES ('Spring');
INSERT INTO forum_test.tags (name) VALUES ('Database');

-- Insert data into users table
INSERT INTO forum_test.users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'admin@example.com', 'Admin', 'User', 'hashed_password', '1234567890', 'adminuser');

INSERT INTO forum_test.users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'mod@example.com', 'Moderator', 'User', 'hashed_password', '1234567890', 'moduser');

INSERT INTO forum_test.users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'user@example.com', 'Regular', 'User', 'hashed_password', '1234567890', 'regularuser');

-- Insert data into posts table
INSERT INTO forum_test.posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (10, 1, NOW(), NOW(), 'This is a post about Java', 'Java Post');

INSERT INTO forum_test.posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (5, 2, NOW(), NOW(), 'This is a post about Spring', 'Spring Post');

INSERT INTO forum_test.posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (2, 3, NOW(), NOW(), 'This is a post about Databases', 'Database Post');

-- Insert data into comments table
INSERT INTO forum_test.comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 3, NOW(), NOW(), 'This is a comment on the Java post');

INSERT INTO forum_test.comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (1, 1, 2, NOW(), NOW(), 'This is a reply to the first comment on the Java post');

INSERT INTO forum_test.comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 1, NOW(), NOW(), 'This is a comment on the Spring post');

-- Insert data into likes table
INSERT INTO forum_test.likes (post_id, user_id, created_at)
VALUES (1, 2, NOW());

INSERT INTO forum_test.likes (post_id, user_id, created_at)
VALUES (1, 3, NOW());

INSERT INTO forum_test.likes (post_id, user_id, created_at)
VALUES (2, 1, NOW());

-- Insert data into post_tags table
INSERT INTO forum_test.post_tags (id, tag_id)
VALUES (1, 1);

INSERT INTO forum_test.post_tags (id, tag_id)
VALUES (2, 2);

INSERT INTO forum_test.post_tags (id, tag_id)
VALUES (3, 3);

-- Insert data into users_roles table
INSERT INTO forum_test.users_roles (role_id, user_id)
VALUES (1, 1);

INSERT INTO forum_test.users_roles (role_id, user_id)
VALUES (2, 2);

INSERT INTO forum_test.users_roles (role_id, user_id)
VALUES (3, 3);
