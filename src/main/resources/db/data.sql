use forum_system;
-- Insert data into roles table
INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('MODERATOR');
INSERT INTO roles (role) VALUES ('USER');

-- Insert data into tags tableINSERT INTO tags (name) VALUES ('Java');
INSERT INTO tags (name) VALUES ('Spring');
INSERT INTO tags (name) VALUES ('Database');
INSERT INTO tags (name) VALUES ('Python');
INSERT INTO tags (name) VALUES ('JavaScript');
INSERT INTO tags (name) VALUES ('SQL');
INSERT INTO tags (name) VALUES ('Hibernate');
INSERT INTO tags (name) VALUES ('REST API');
INSERT INTO tags (name) VALUES ('GraphQL');
INSERT INTO tags (name) VALUES ('Microservices');
INSERT INTO tags (name) VALUES ('Cloud Computing');
INSERT INTO tags (name) VALUES ('DevOps');
INSERT INTO tags (name) VALUES ('Docker');
INSERT INTO tags (name) VALUES ('Kubernetes');
INSERT INTO tags (name) VALUES ('Machine Learning');
INSERT INTO tags (name) VALUES ('AII');
INSERT INTO tags (name) VALUES ('Big Data');
INSERT INTO tags (name) VALUES ('Cybersecurity');
INSERT INTO tags (name) VALUES ('Web Development');
INSERT INTO tags (name) VALUES ('Mobile Development');
INSERT INTO tags (name) VALUES ('Blockchain');
INSERT INTO tags (name) VALUES ('Graph Databases');
INSERT INTO tags (name) VALUES ('NoSQL');
INSERT INTO tags (name) VALUES ('Concurrency');
INSERT INTO tags (name) VALUES ('Software Design');
INSERT INTO tags (name) VALUES ('Algorithms');
INSERT INTO tags (name) VALUES ('Data Structures');
INSERT INTO tags (name) VALUES ('Performance Tuning');
INSERT INTO tags (name) VALUES ('System Design');
INSERT INTO tags (name) VALUES ('UI/UX Design');
INSERT INTO tags (name) VALUES ('API Design');
INSERT INTO tags (name) VALUES ('Data Science');
INSERT INTO tags (name) VALUES ('Statistical Analysis');
INSERT INTO tags (name) VALUES ('SaaS');
INSERT INTO tags (name) VALUES ('Enterprise Architecture');
INSERT INTO tags (name) VALUES ('IT Management');

-- Insert data into users table
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'admin@example.com', 'Admin', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'adminuser');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'mod@example.com', 'Moderator', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'moduser');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username)
VALUES (0, NOW(), NOW(), 'user@example.com', 'Regular', 'User', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user2@example.com', 'Regular2', 'User2', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser2', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user3@example.com', 'Regular3', 'User3', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser3','https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user4@example.com', 'Regular4', 'User4', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser4', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user5@example.com', 'Regular5', 'User5', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser5','https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');
INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user6@example.com', 'Regular6', 'User6', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser6', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (1, NOW(), NOW(), 'user7@example.com', 'Regular7', 'User7', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser7', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user8@example.com', 'Regular8', 'User8', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser8', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (1, NOW(), NOW(), 'user9@example.com', 'Regular9', 'User9', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser9', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user10@example.com', 'Regular10', 'User10', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser10', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (1, NOW(), NOW(), 'user11@example.com', 'Regular11', 'User11', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser11', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user12@example.com', 'Regular12', 'User12', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser12', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user13@example.com', 'Regular13', 'User13', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser13', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user14@example.com', 'Regular14', 'User14', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser14', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user15@example.com', 'Regular15', 'User15', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser15', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (1, NOW(), NOW(), 'user16@example.com', 'Regular16', 'User16', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser16', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user17@example.com', 'Regular17', 'User17', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser17', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user18@example.com', 'Regular18', 'User18', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser18', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (1, NOW(), NOW(), 'user19@example.com', 'Regular19', 'User19', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser19', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');

INSERT INTO users (is_blocked, created_at, updated_at, email, first_name, last_name, password_hash, phone_number, username, photo_url)
VALUES (0, NOW(), NOW(), 'user20@example.com', 'Regular20', 'User20', '$2a$10$sdpmlDKxUGC73gaFsP2rH.QIzONcOwVxqxaVHF5Q47Vyi9ci/IlLW', '1234567890', 'regularuser20', 'https://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png');
-- Insert data into posts table
INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (10, 1, NOW(), NOW(), 'This is a detailed post about Java programming. Java is a versatile language used in various applications including web, mobile, and enterprise systems. Its robust ecosystem and strong community support make it a popular choice for developers.', 'Introduction to Java Programming');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (5, 2, NOW(), NOW(), 'Spring is a powerful framework that simplifies Java development. It provides comprehensive infrastructure support for developing Java applications. Spring promotes good design practices and reduces boilerplate code, making it a favorite among developers.', 'Exploring Spring Framework Features');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (2, 3, NOW(), NOW(), 'Databases are critical components in modern applications. They store and manage data efficiently, supporting complex queries and transactions. This post discusses different types of databases including relational and NoSQL databases, their uses, and their advantages.', 'Understanding Databases: Relational vs NoSQL');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (8, 4, NOW(), NOW(), 'JavaScript is a dynamic language essential for modern web development. It allows for interactive web pages and has grown beyond its origins with the advent of frameworks like React and Node.js. This post explores its evolution and key features.', 'The Evolution of JavaScript and Modern Frameworks');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (12, 5, NOW(), NOW(), 'Python is a high-level programming language known for its readability and simplicity. It is widely used in web development, data analysis, artificial intelligence, and more. This post covers the basics and some advanced topics in Python.', 'Getting Started with Python: From Basics to Advanced');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (7, 6, NOW(), NOW(), 'Docker simplifies the deployment process by allowing applications to be containerized. Containers are lightweight and provide consistent environments for development, testing, and production. This post delves into Docker basics and best practices.', 'Docker: Containerization and Best Practices');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (6, 7, NOW(), NOW(), 'Kubernetes is an open-source platform for managing containerized applications. It automates deployment, scaling, and operations, making it essential for large-scale applications. This post provides an overview of Kubernetes architecture and its components.', 'An Introduction to Kubernetes and Its Architecture');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (9, 8, NOW(), NOW(), 'Machine Learning is a subset of artificial intelligence that focuses on building systems that can learn from data. This post discusses various machine learning techniques and their applications in different domains.', 'Machine Learning: Techniques and Applications');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (4, 9, NOW(), NOW(), 'Cloud computing offers scalable and flexible IT resources over the internet. It has revolutionized how businesses operate and provides numerous benefits including cost savings, flexibility, and accessibility. This post explores cloud computing services and their impact.', 'The Benefits of Cloud Computing');

INSERT INTO posts (likes_count, user_id, created_at, updated_at, content, title)
VALUES (11, 10, NOW(), NOW(), 'Agile methodologies promote iterative development and continuous feedback. This post examines various Agile practices including Scrum and Kanban, and their impact on software development projects.', 'Agile Methodologies: Scrum, Kanban, and Beyond');

-- Insert data into comments table
INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 3, NOW(), NOW(), 'This is a comment on the Java post');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (1, 1, 2, NOW(), NOW(), 'This is a reply to the first comment on the Java post');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 1, NOW(), NOW(), 'This is a comment on the Spring post');
INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 1, NOW(), NOW(), 'This is a comment on the Java post by user 1');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (1, 1, 2, NOW(), NOW(), 'This is a reply to the first comment on the Java post by user 2');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 3, NOW(), NOW(), 'This is a comment on the Spring post by user 3');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 4, NOW(), NOW(), 'Another comment on the Java post by user 4');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (4, 1, 5, NOW(), NOW(), 'Reply to the second comment on the Java post by user 5');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 6, NOW(), NOW(), 'Another comment on the Spring post by user 6');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 3, 7, NOW(), NOW(), 'This is a comment on the Database post by user 7');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (7, 3, 8, NOW(), NOW(), 'Reply to the comment on the Database post by user 8');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 9, NOW(), NOW(), 'Comment on the Java post by user 9');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (9, 1, 10, NOW(), NOW(), 'Reply to the comment on the Java post by user 10');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 11, NOW(), NOW(), 'Comment on the Spring post by user 11');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (11, 2, 12, NOW(), NOW(), 'Reply to the comment on the Spring post by user 12');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 3, 13, NOW(), NOW(), 'Another comment on the Database post by user 13');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (13, 3, 14, NOW(), NOW(), 'Reply to the comment on the Database post by user 14');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 1, 15, NOW(), NOW(), 'Yet another comment on the Java post by user 15');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (15, 1, 16, NOW(), NOW(), 'Reply to another comment on the Java post by user 16');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 2, 17, NOW(), NOW(), 'Another comment on the Spring post by user 17');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (17, 2, 18, NOW(), NOW(), 'Reply to another comment on the Spring post by user 18');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (NULL, 3, 19, NOW(), NOW(), 'Final comment on the Database post by user 19');

INSERT INTO comments (parent_comment_id, post_id, user_id, created_at, updated_at, content)
VALUES (19, 3, 20, NOW(), NOW(), 'Reply to the final comment on the Database post by user 20');

-- Insert data into likes table
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 2, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 3, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 1, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 4, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 5, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 6, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 7, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 8, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 9, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 10, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 11, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 12, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 13, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 14, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 15, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 16, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 17, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (3, 18, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (2, 19, NOW());
INSERT INTO likes (post_id, user_id, created_at) VALUES (1, 20, NOW());

-- Insert data into post_tags table
INSERT INTO post_tags (id, tag_id) VALUES (1, 1);
INSERT INTO post_tags (id, tag_id) VALUES (1, 2);
INSERT INTO post_tags (id, tag_id) VALUES (1, 3);
INSERT INTO post_tags (id, tag_id) VALUES (1, 4);

-- Post 2 associations
INSERT INTO post_tags (id, tag_id) VALUES (2, 1);
INSERT INTO post_tags (id, tag_id) VALUES (2, 3);
INSERT INTO post_tags (id, tag_id) VALUES (2, 5);

-- Post 3 associations
INSERT INTO post_tags (id, tag_id) VALUES (3, 2);
INSERT INTO post_tags (id, tag_id) VALUES (3, 4);
INSERT INTO post_tags (id, tag_id) VALUES (3, 6);

-- Additional associations for more diversity
-- Post 4 associations
INSERT INTO post_tags (id, tag_id) VALUES (4, 1);
INSERT INTO post_tags (id, tag_id) VALUES (4, 5);
INSERT INTO post_tags (id, tag_id) VALUES (4, 7);

-- Post 5 associations
INSERT INTO post_tags (id, tag_id) VALUES (5, 2);
INSERT INTO post_tags (id, tag_id) VALUES (5, 6);
INSERT INTO post_tags (id, tag_id) VALUES (5, 8);

-- Post 6 associations
INSERT INTO post_tags (id, tag_id) VALUES (6, 3);
INSERT INTO post_tags (id, tag_id) VALUES (6, 4);
INSERT INTO post_tags (id, tag_id) VALUES (6, 9);

-- Post 7 associations
INSERT INTO post_tags (id, tag_id) VALUES (7, 1);
INSERT INTO post_tags (id, tag_id) VALUES (7, 2);
INSERT INTO post_tags (id, tag_id) VALUES (7, 5);
INSERT INTO post_tags (id, tag_id) VALUES (7, 10);

-- Post 8 associations
INSERT INTO post_tags (id, tag_id) VALUES (8, 3);
INSERT INTO post_tags (id, tag_id) VALUES (8, 6);
INSERT INTO post_tags (id, tag_id) VALUES (8, 7);

-- Post 9 associations
INSERT INTO post_tags (id, tag_id) VALUES (9, 4);
INSERT INTO post_tags (id, tag_id) VALUES (9, 8);
INSERT INTO post_tags (id, tag_id) VALUES (9, 9);

-- Insert data into users_roles table
-- User 1 is assigned Admin role (role_id = 1)
INSERT INTO users_roles (role_id, user_id) VALUES (1, 1);

-- User 2 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 2);

-- User 3 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 3);

-- User 4 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 4);

-- User 5 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 5);

-- User 6 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 6);

-- User 7 is assigned Admin role (role_id = 1)
INSERT INTO users_roles (role_id, user_id) VALUES (1, 7);

-- User 8 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 8);

-- User 9 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 9);

-- User 10 is assigned Admin role (role_id = 1)
INSERT INTO users_roles (role_id, user_id) VALUES (1, 10);

-- User 11 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 11);

-- User 12 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 12);

-- User 13 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 13);

-- User 14 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 14);

-- User 15 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 15);

-- User 16 is assigned Admin role (role_id = 1)
INSERT INTO users_roles (role_id, user_id) VALUES (1, 16);

-- User 17 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 17);

-- User 18 is assigned Regular User role (role_id = 3)
INSERT INTO users_roles (role_id, user_id) VALUES (3, 18);

-- User 19 is assigned Moderator role (role_id = 2)
INSERT INTO users_roles (role_id, user_id) VALUES (2, 19);

-- User 20 is assigned Admin role (role_id = 1)
INSERT INTO users_roles (role_id, user_id) VALUES (1, 20);