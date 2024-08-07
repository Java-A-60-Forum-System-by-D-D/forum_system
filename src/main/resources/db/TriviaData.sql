-- Insert categories
INSERT INTO forum_test.categories (category_name, description) VALUES
                                                                   ('Interesting Facts', 'Mind-blowing facts from various fields'),
                                                                   ('Humor', 'Funny stories and jokes'),
                                                                   ('Did You Know?', 'Surprising trivia and lesser-known facts');

-- Insert roles
INSERT INTO forum_test.roles (role) VALUES
                                        ('ADMIN'),
                                        ('MODERATOR'),
                                        ('USER');

-- Insert tags
INSERT INTO forum_test.tags (name) VALUES
                                       ('Science'),
                                       ('History'),
                                       ('Technology'),
                                       ('Nature'),
                                       ('Funny'),
                                       ('Mindblowing'),
                                       ('Trivia');

-- Insert users with the provided password hash
INSERT INTO forum_test.users (is_blocked, created_at, updated_at, first_name, last_name, password_hash, email, phone_number, photo_url, username) VALUES
                                                                                                                                                      (0, NOW(), NOW(), 'John', 'Doe', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2', 'john@example.com', '1234567890', 'https://example.com/john.jpg', 'johnd'),
                                                                                                                                                      (0, NOW(), NOW(), 'Jane', 'Smith', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2', 'jane@example.com', '0987654321', 'https://example.com/jane.jpg', 'janes'),
                                                                                                                                                      (0, NOW(), NOW(), 'Bob', 'Johnson', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2', 'bob@example.com', '1122334455', 'https://example.com/bob.jpg', 'bobj');

-- Insert posts
INSERT INTO forum_test.posts (likes_count, user_id, created_At, updated_at, title, content, category_id) VALUES
                                                                                                             (10, 1, NOW(), NOW(), 'Fascinating Ant Facts', 'Did you know that ants can lift up to 50 times their own body weight? That''s like a human lifting a truck!', 1),
                                                                                                             (5, 2, NOW(), NOW(), 'Why did the scarecrow win an award?', 'He was outstanding in his field!', 2),
                                                                                                             (8, 3, NOW(), NOW(), 'The Great Molasses Flood', 'In 1919, a molasses tank burst in Boston, creating a sticky flood that moved at 35 mph (56 km/h) through the streets!', 3);

-- Insert comments
INSERT INTO forum_test.comments (parent_comment_id, post_id, user_id, created_At, updated_at, content) VALUES
                                                                                                           (NULL, 1, 2, NOW(), NOW(), 'Wow, ants are incredible!'),
                                                                                                           (NULL, 2, 3, NOW(), NOW(), 'Haha, that''s a good one!'),
                                                                                                           (NULL, 3, 1, NOW(), NOW(), 'I can''t believe this actually happened!');

-- Insert likes
INSERT INTO forum_test.likes (post_id, user_id, created_At) VALUES
                                                                (1, 2, NOW()),
                                                                (1, 3, NOW()),
                                                                (2, 1, NOW()),
                                                                (3, 2, NOW());

-- Insert post tags
INSERT INTO forum_test.post_tags (id, tag_id) VALUES
                                                  (1, 4), -- Ant fact tagged with 'Nature'
                                                  (1, 6), -- Ant fact tagged with 'Mindblowing'
                                                  (2, 5), -- Joke tagged with 'Funny'
                                                  (3, 2), -- Molasses flood tagged with 'History'
                                                  (3, 7); -- Molasses flood tagged with 'Trivia'

-- Insert user roles
INSERT INTO forum_test.users_roles (role_Id, user_id) VALUES
                                                          (1, 1), -- John is an ADMIN
                                                          (2, 2), -- Jane is a MODERATOR
                                                          (3, 3); -- Bob is a USER