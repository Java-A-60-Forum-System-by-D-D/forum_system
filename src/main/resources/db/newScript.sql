# use FORUM_TEST;
-- Populate roles
use forum_system;
INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('MODERATOR'),
       ('USER');

-- Populate categories
INSERT INTO categories (category_name, description)
VALUES ('Science', 'Fascinating scientific discoveries'),
       ('History', 'Intriguing historical events'),
       ('Technology', 'Latest tech innovations'),
       ('Nature', 'Wonders of the natural world'),
       ('Space', 'Cosmic facts and phenomena'),
       ('Human Body', 'Amazing facts about human anatomy'),
       ('Ancient Civilizations', 'Mysteries of past cultures'),
       ('Inventions', 'Groundbreaking inventions'),
       ('Oceans', 'Secrets of the deep seas'),
       ('Psychology', 'Interesting human behaviors');

-- Populate users with the same password hash for all users
INSERT INTO users (is_blocked, created_at, updated_at, first_name, last_name, password_hash, email, phone_number,
                   photo_url, username)
VALUES (0, NOW(), NOW(), 'John', 'e', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'john@example.com', '1234567890',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'johnd'),
       (0, NOW(), NOW(), 'Jane', 'Smith', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'jane@example.com', '2345678901',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'janes'),
       (0, NOW(), NOW(), 'Mike', 'Johnson', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'mike@example.com', '3456789012',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'mikej'),
       (0, NOW(), NOW(), 'Emily', 'Brown', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'emily@example.com', '4567890123',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'emilyb'),
       (0, NOW(), NOW(), 'David', 'Leee', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'david@example.com', '5678901234',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'davidl'),
       (0, NOW(), NOW(), 'Sarah', 'Wilson', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'sarah@example.com', '6789012345',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'sarahw'),
       (0, NOW(), NOW(), 'Chris', 'Taylor', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'chris@example.com', '7890123456',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'christ'),
       (0, NOW(), NOW(), 'Lisa', 'Anderson', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'lisa@example.com', '8901234567',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'lisaa'),
       (0, NOW(), NOW(), 'Tomy', 'Martin', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'tom@example.com', '9012345678',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'tomm'),
       (0, NOW(), NOW(), 'Emma', 'White', '$2a$10$rrjF9VyOCpbky9ORAbCDeObUV2KU7HdlBTKhSBOoytGo1sa0UzoI2',
        'emma@example.com', '0123456789',
        'http://res.cloudinary.com/dtwfzrl2v/image/upload/v1721740155/l4pjsbzmpyk5jfknwf4q.png', 'emmaw');

-- Populate posts
INSERT INTO posts (category_id, user_id, created_at, updated_at, title, content, likes_count)
VALUES (1, 1, NOW(), NOW(), 'Discovery of Water on Mars',
        'Scientists have found evidence of liquid water on Mars, which could have huge implications for future exploration missions.',
        0),
       (2, 2, NOW(), NOW(), 'Ancient Lost City Discovered',
        'Archaeologists have uncovered an ancient city hidden deep within the Amazon rainforest, shedding new light on early civilizations.',
        0),
       (3, 3, NOW(), NOW(), 'Breakthrough in AI Technology',
        'A new AI model has been developed that can predict protein structures with unprecedented accuracy, paving the way for medical advancements.',
        0),
       (4, 4, NOW(), NOW(), 'New Species Found in Andes',
        'A previously unknown species of frog has been discovered in the remote regions of the Andes mountains, highlighting the region''s biodiversity.',
        0),
       (5, 5, NOW(), NOW(), 'Exoplanet Discovery by NASA',
        'NASA has detected an Earth-like exoplanet in the habitable zone of a distant star, increasing the chances of finding extraterrestrial life.',
        0),
       (1, 6, NOW(), NOW(), 'Advances in Renewable Energy',
        'New breakthroughs in solar and wind technology are making renewable energy sources more efficient and affordable.',
        0),
       (2, 7, NOW(), NOW(), 'Archaeological Find in Egypt',
        'A recently discovered tomb in Egypt contains artifacts that could reshape our understanding of ancient pharaohs.',
        0),
       (3, 8, NOW(), NOW(), 'Quantum Computing Progress',
        'Researchers have made significant strides in quantum computing, potentially revolutionizing fields from cryptography to materials science.',
        0),
       (4, 9, NOW(), NOW(), 'Ocean Exploration Milestone',
        'A new deep-sea vehicle has reached unprecedented depths, capturing never-before-seen footage of underwater ecosystems.',
        0),
       (5, 10, NOW(), NOW(), 'Health Benefits of Plant-Based Diets',
        'Studies show that adopting a plant-based diet can lead to significant improvements in heart health and longevity.',
        0);

-- Вмъкване на коментари
INSERT INTO comments (post_id, user_id, created_at, updated_at, content)
VALUES (1, 2, NOW(), NOW(),
        'This is groundbreaking news! What are the potential implications for future Mars missions and human exploration?'),
       (2, 3, NOW(), NOW(),
        'It is fascinating to think about how old this city might be and what it can tell us about early civilizations.'),
       (3, 4, NOW(), NOW(),
        'The possibilities for medical advancements with this AI breakthrough are incredibly exciting!'),
       (4, 5, NOW(), NOW(),
        'The discovery of new species reminds us of how much we still don''t know about our planet''s biodiversity.'),
       (5, 1, NOW(), NOW(),
        'This exoplanet could be one of the most significant discoveries in our search for life beyond Earth.'),
       (1, 7, NOW(), NOW(), 'This could be a game-changer in how we address the global energy crisis.'),
       (2, 8, NOW(), NOW(),
        'The new discoveries from this tomb are fascinating! They might rewrite some chapters of history.'),
       (3, 9, NOW(), NOW(),
        'Quantum computing is moving faster than expected. The implications for industries are immense.'),
       (4, 10, NOW(), NOW(), 'Exploring the depths of our oceans can reveal as many mysteries as space exploration.'),
       (5, 6, NOW(), NOW(), 'The positive impact of plant-based diets on health is becoming clearer with each study.');

-- Populate comment replies
INSERT INTO comments (parent_comment_id, post_id, user_id, created_At, updated_at, content)
VALUES (1, 1, 3, NOW(), NOW(), 'This could indeed lead to significant advancements in life-support systems.'),
       (2, 2, 4, NOW(), NOW(), 'The history behind this discovery is truly remarkable. It could reveal so much.'),
       (3, 3, 5, NOW(), NOW(), 'Yes, drug discovery will greatly benefit from these AI advancements.'),
       (4, 4, 6, NOW(), NOW(), 'Protecting habitats is essential. Conservation efforts are key to biodiversity.'),
       (5, 5, 7, NOW(), NOW(), 'The James Webb telescope will surely bring us more amazing discoveries soon.'),
       (6, 6, 8, NOW(), NOW(), 'Alzheimer\'s research could be revolutionized by these findings. It\'s promising.'),
       (7, 7, 9, NOW(), NOW(), 'These findings could change what we know about ancient civilizations entirely.'),
       (8, 8, 10, NOW(), NOW(), 'True, regulatory barriers are always a challenge for new technologies like this.'),
       (9, 9, 1, NOW(), NOW(), 'Deep-sea exploration deserves much more attention and funding in the future.'),
       (10, 10, 2, NOW(), NOW(), 'Dreams are indeed a fascinating reflection of shared human experiences.');
-- Populate tags
INSERT INTO tags (name)
VALUES ('Mars'),
       ('Archaeology'),
       ('AI'),
       ('Biology'),
       ('Astronomy'),
       ('Neuroscience'),
       ('Ancient History'),
       ('Transportation'),
       ('Marine Biology'),
       ('Psychology');

-- Populate post_tags
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

-- Populate users_roles (assuming all users are regular users)
INSERT INTO users_roles (role_Id, user_id)
SELECT 3, id
FROM users;

-- Make the first user an admin and the second a moderator
UPDATE users_roles
SET role_Id = 1
WHERE user_id = 1;
UPDATE users_roles
SET role_Id = 2
WHERE user_id = 2;