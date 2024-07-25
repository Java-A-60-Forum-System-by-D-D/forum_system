-- Insert roles
INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('MODERATOR'),
       ('USER');

-- Insert tags
INSERT INTO tags (name)
VALUES ('Java'),
       ('Spring'),
       ('SQL'),
       ('REST'),
       ('API'),
       ('Backend'),
       ('Frontend'),
       ('DevOps'),
       ('Database'),
       ('Security');

-- Insert users with hashed passwords (bcrypt) - like "password1", "password2"... and phone numbers for admins
INSERT INTO users (is_blocked, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, 'admin1@example.com', 'Alice', 'Admin', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        '1234567890', 'admin1'),
       (0, 'admin2@example.com', 'Bob', 'Admin', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        '1234567891', 'admin2'),
       (0, 'user1@example.com', 'John', 'Doe', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user1'),
       (0, 'user2@example.com', 'Jane', 'Doe', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user2'),
       (0, 'user3@example.com', 'Jim', 'Beam', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user3'),
       (0, 'user4@example.com', 'Jack', 'Daniels', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user4'),
       (0, 'user5@example.com', 'Johnny', 'Walker', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        NULL, 'user5'),
       (0, 'user6@example.com', 'Tom', 'Cruise', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user6'),
       (0, 'user7@example.com', 'Scarlett', 'Johansson', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        NULL, 'user7'),
       (0, 'user8@example.com', 'Chris', 'Evans', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', NULL,
        'user8'),
       (0, 'user9@example.com', 'Robert', 'Downey', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        NULL, 'user9'),
       (0, 'user10@example.com', 'Mark', 'Ruffalo', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW',
        NULL, 'user10');

-- Insert posts
INSERT INTO posts (likes_count, user_id, content, title)
VALUES (5, 1, 'This is the content of post 1', 'Post 1'),
       (10, 2, 'This is the content of post 2', 'Post 2'),
       (15, 3, 'This is the content of post 3', 'Post 3'),
       (20, 4, 'This is the content of post 4', 'Post 4'),
       (25, 5, 'This is the content of post 5', 'Post 5'),
       (30, 6, 'This is the content of post 6', 'Post 6'),
       (35, 7, 'This is the content of post 7', 'Post 7'),
       (40, 8, 'This is the content of post 8', 'Post 8'),
       (45, 9, 'This is the content of post 9', 'Post 9'),
       (50, 10, 'This is the content of post 10', 'Post 10');

-- Insert comments
INSERT INTO comments (parent_comment_id, post_id, user_id, content)
VALUES (NULL, 1, 1, 'This is a comment on post 1 by admin1'),
       (NULL, 2, 2, 'This is a comment on post 2 by admin2'),
       (NULL, 3, 3, 'This is a comment on post 3 by user1'),
       (NULL, 4, 4, 'This is a comment on post 4 by user2'),
       (NULL, 5, 5, 'This is a comment on post 5 by user3'),
       (NULL, 6, 6, 'This is a comment on post 6 by user4'),
       (NULL, 7, 7, 'This is a comment on post 7 by user5'),
       (NULL, 8, 8, 'This is a comment on post 8 by user6'),
       (NULL, 9, 9, 'This is a comment on post 9 by user7'),
       (NULL, 10, 10, 'This is a comment on post 10 by user8');

-- Insert likes
INSERT INTO likes (post_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Insert post_tags
INSERT INTO post_tags (id, tag_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Assign roles to users
INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1), -- Admin role to admin1
       (1, 2), -- Admin role to admin2
       (3, 3), -- User role to user1
       (3, 4), -- User role to user2
       (3, 5), -- User role to user3
       (3, 6), -- User role to user4
       (3, 7), -- User role to user5
       (3, 8), -- User role to user6
       (3, 9), -- User role to user7
       (3, 10); -- User role to user8
