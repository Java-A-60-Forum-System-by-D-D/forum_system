-- Populate roles
INSERT INTO forum_test.roles (role) VALUES
                                        ('ADMIN'), ('MODERATOR'), ('USER');

-- Populate categories
INSERT INTO forum_test.categories (category_name, description) VALUES
                                                                   ('Science', 'Fascinating scientific discoveries'),
                                                                   ('History', 'Intriguing historical events'),
                                                                   ('Technology', 'Latest tech innovations'),
                                                                   ('Nature', 'Wonders of the natural world'),
                                                                   ('Space', 'Cosmic facts and phenomena'),
                                                                   ('Human Body', 'Amazing facts about human anatomy'),
                                                                   ('Ancient Civilizations', 'Mysteries of past cultures'),
                                                                   ('Inventions', 'Groundbreaking inventions'),
                                                                   ('Oceans', 'Secrets of the deep seas'),
                                                                   ('Psychology', 'Interesting human behaviors');

-- Populate users
INSERT INTO forum_test.users (is_blocked, created_at, updated_at, first_name, last_name, password_hash, email, phone_number, photo_url, username) VALUES
                                                                                                                                                      (0, NOW(), NOW(), 'John', 'Doe', 'hash1', 'john@example.com', '1234567890', 'photo1.jpg', 'johnd'),
                                                                                                                                                      (0, NOW(), NOW(), 'Jane', 'Smith', 'hash2', 'jane@example.com', '2345678901', 'photo2.jpg', 'janes'),
                                                                                                                                                      (0, NOW(), NOW(), 'Mike', 'Johnson', 'hash3', 'mike@example.com', '3456789012', 'photo3.jpg', 'mikej'),
                                                                                                                                                      (0, NOW(), NOW(), 'Emily', 'Brown', 'hash4', 'emily@example.com', '4567890123', 'photo4.jpg', 'emilyb'),
                                                                                                                                                      (0, NOW(), NOW(), 'David', 'Lee', 'hash5', 'david@example.com', '5678901234', 'photo5.jpg', 'davidl'),
                                                                                                                                                      (0, NOW(), NOW(), 'Sarah', 'Wilson', 'hash6', 'sarah@example.com', '6789012345', 'photo6.jpg', 'sarahw'),
                                                                                                                                                      (0, NOW(), NOW(), 'Chris', 'Taylor', 'hash7', 'chris@example.com', '7890123456', 'photo7.jpg', 'christ'),
                                                                                                                                                      (0, NOW(), NOW(), 'Lisa', 'Anderson', 'hash8', 'lisa@example.com', '8901234567', 'photo8.jpg', 'lisaa'),
                                                                                                                                                      (0, NOW(), NOW(), 'Tom', 'Martin', 'hash9', 'tom@example.com', '9012345678', 'photo9.jpg', 'tomm'),
                                                                                                                                                      (0, NOW(), NOW(), 'Emma', 'White', 'hash10', 'emma@example.com', '0123456789', 'photo10.jpg', 'emmaw');

-- Populate posts
INSERT INTO forum_test.posts (category_id, user_id, created_At, updated_at, title, content, likes_count) VALUES
                                                                                                             (1, 1, NOW(), NOW(), 'Water on Mars', 'Scientists have found evidence of liquid water on Mars.', 0),
                                                                                                             (2, 2, NOW(), NOW(), 'Lost city discovered', 'Archaeologists uncover ancient city in the Amazon rainforest.', 0),
                                                                                                             (3, 3, NOW(), NOW(), 'AI breakthrough', 'New AI model can predict protein structures with high accuracy.', 0),
                                                                                                             (4, 4, NOW(), NOW(), 'New species found', 'Rare frog species discovered in the Andes mountains.', 0),
                                                                                                             (5, 5, NOW(), NOW(), 'Exoplanet discovery', 'NASA telescope detects Earth-like planet in habitable zone.', 0),
                                                                                                             (6, 6, NOW(), NOW(), 'Brain plasticity', 'Study shows adult brains can form new neurons throughout life.', 0),
                                                                                                             (7, 7, NOW(), NOW(), 'Göbekli Tepe mystery', 'New findings at ancient Turkish site challenge historical timeline.', 0),
                                                                                                             (8, 8, NOW(), NOW(), 'Flying car prototype', 'First successful test flight of a commercial flying car.', 0),
                                                                                                             (9, 9, NOW(), NOW(), 'Deep sea creatures', 'Bioluminescent organisms found in Mariana Trench.', 0),
                                                                                                             (10, 10, NOW(), NOW(), 'Dream study results', 'Research reveals common themes in dreams across cultures.', 0);

-- Populate comments
INSERT INTO forum_test.comments (post_id, user_id, created_At, updated_at, content) VALUES
                                                                                        (1, 2, NOW(), NOW(), 'This is groundbreaking! What are the implications for future Mars missions?'),
                                                                                        (2, 3, NOW(), NOW(), 'I wonder how old this city is and what civilization built it.'),
                                                                                        (3, 4, NOW(), NOW(), 'The potential applications in medicine are exciting!'),
                                                                                        (4, 5, NOW(), NOW(), 'It''s amazing how many undiscovered species are still out there.'),
                                                                                        (5, 6, NOW(), NOW(), 'I hope we can send probes to study this planet more closely.'),
                                                                                        (6, 7, NOW(), NOW(), 'This gives hope for treating neurodegenerative diseases.'),
                                                                                        (7, 8, NOW(), NOW(), 'How does this change our understanding of early human civilization?'),
                                                                                        (8, 9, NOW(), NOW(), 'I can''t wait to see these in action! When will they be available?'),
                                                                                        (9, 10, NOW(), NOW(), 'The deep sea is like another world. So much to explore!'),
                                                                                        (10, 1, NOW(), NOW(), 'I''ve always been fascinated by dream interpretation. Great study!');

-- Populate comment replies
INSERT INTO forum_test.comments (parent_comment_id, post_id, user_id, created_At, updated_at, content) VALUES
                                                                                                           (1, 1, 3, NOW(), NOW(), 'It could lead to more advanced life-support systems for astronauts.'),
                                                                                                           (2, 2, 4, NOW(), NOW(), 'Some estimates suggest it could be over 3000 years old!'),
                                                                                                           (3, 3, 5, NOW(), NOW(), 'Agreed! It could revolutionize drug discovery processes.'),
                                                                                                           (4, 4, 6, NOW(), NOW(), 'Biodiversity is crucial. We need to protect these habitats.'),
                                                                                                           (5, 5, 7, NOW(), NOW(), 'The James Webb telescope might give us more data soon.'),
                                                                                                           (6, 6, 8, NOW(), NOW(), 'This research could lead to new therapies for Alzheimer''s.'),
                                                                                                           (7, 7, 9, NOW(), NOW(), 'It suggests advanced societies existed earlier than we thought.'),
                                                                                                           (8, 8, 10, NOW(), NOW(), 'Probably not for a while. There are still many regulations to overcome.'),
                                                                                                           (9, 9, 1, NOW(), NOW(), 'I''d love to see more funding for deep-sea exploration!'),
                                                                                                           (10, 10, 2, NOW(), NOW(), 'It''s interesting how dreams can reflect our shared human experiences.');

-- Populate tags
INSERT INTO forum_test.tags (name) VALUES
                                       ('Mars'), ('Archaeology'), ('AI'), ('Biology'), ('Astronomy'),
                                       ('Neuroscience'), ('Ancient History'), ('Transportation'), ('Marine Biology'), ('Psychology');

-- Populate post_tags
INSERT INTO forum_test.post_tags (id, tag_id) VALUES
                                                  (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
                                                  (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- Populate users_roles (assuming all users are regular users)
INSERT INTO forum_test.users_roles (role_Id, user_id)
SELECT 3, id FROM forum_test.users;

-- Make the first user an admin and the second a moderator
UPDATE forum_test.users_roles SET role_Id = 1 WHERE user_id = 1;
UPDATE forum_test.users_roles SET role_Id = 2 WHERE user_id = 2;