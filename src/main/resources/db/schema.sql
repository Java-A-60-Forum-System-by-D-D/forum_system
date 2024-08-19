# USE FORUM_TEST;
use forum_system;
-- Create or replace the categories table
CREATE OR REPLACE TABLE categories (
                                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                                  category_name VARCHAR(30) NULL,
                                                  description VARCHAR(50) NULL
);

-- Create or replace the roles table
CREATE OR REPLACE TABLE roles (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             role ENUM('ADMIN', 'MODERATOR', 'USER') NULL
);

-- Create or replace the spring_session table
CREATE OR REPLACE TABLE spring_session (
                                                      PRIMARY_ID CHAR(36) NOT NULL PRIMARY KEY,
                                                      SESSION_ID CHAR(36) NOT NULL,
                                                      CREATION_TIME BIGINT NOT NULL,
                                                      LAST_ACCESS_TIME BIGINT NOT NULL,
                                                      MAX_INACTIVE_INTERVAL INT NOT NULL,
                                                      EXPIRY_TIME BIGINT NOT NULL,
                                                      PRINCIPAL_NAME VARCHAR(100) NULL,
                                                      CONSTRAINT SPRING_SESSION_IX1 UNIQUE (SESSION_ID)
) ROW_FORMAT=DYNAMIC;

CREATE OR REPLACE INDEX SPRING_SESSION_IX2 ON spring_session (EXPIRY_TIME);
CREATE OR REPLACE INDEX SPRING_SESSION_IX3 ON spring_session (PRINCIPAL_NAME);

-- Create or replace the spring_session_attributes table
CREATE OR REPLACE TABLE spring_session_attributes (
                                                                 SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                                                 ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                                                 ATTRIBUTE_BYTES BLOB NOT NULL,
                                                                 PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                                                 CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID)
                                                                     REFERENCES spring_session (PRIMARY_ID) ON DELETE CASCADE
) ROW_FORMAT=DYNAMIC;

CREATE OR REPLACE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON spring_session_attributes (SESSION_PRIMARY_ID);

-- Create or replace the tags table
CREATE OR REPLACE TABLE tags (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            name VARCHAR(255) NULL
);

-- Create or replace the users table
CREATE OR REPLACE TABLE users (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             is_blocked BIT NULL,
                                             created_at DATETIME(6) NULL,
                                             updated_at DATETIME(6) NULL,
                                             first_name VARCHAR(32) NOT NULL,
                                             last_name VARCHAR(32) NOT NULL,
                                             password_hash VARCHAR(100) NOT NULL,
                                             email VARCHAR(255) NOT NULL,
                                             phone_number VARCHAR(255) NULL,
                                             photo_url VARCHAR(255) NULL,
                                             username VARCHAR(255) NOT NULL,
                                             CONSTRAINT UK23y4gd49ajvbqgl3psjsvhff6 UNIQUE (username),
                                             CONSTRAINT UKncoa9bfasrql0x4nhmh1plxxy UNIQUE (email)
);

-- Create or replace the posts table with ON DELETE CASCADE on the user_id foreign key
CREATE OR REPLACE TABLE posts (
                                             category_id INT NULL,
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             likes_count INT NULL,
                                             user_id INT NOT NULL,
                                             created_At DATETIME(6) NULL,
                                             updated_at DATETIME(6) NULL,
                                             title VARCHAR(64) NOT NULL,
                                             content VARCHAR(8192) NOT NULL,
                                             CONSTRAINT FKeatd60h39ra63bdk44w8vfvk2 FOREIGN KEY (category_id)
                                                 REFERENCES categories (id),
                                             CONSTRAINT FKqwy1e63idnvjerwvc47tq3k5 FOREIGN KEY (user_id)
                                                 REFERENCES users (id) ON DELETE CASCADE
);

-- Create or replace the comments table with ON DELETE CASCADE on post_id and user_id foreign keys
CREATE OR REPLACE TABLE comments (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                parent_comment_id INT NULL,
                                                post_id INT NOT NULL,
                                                user_id INT NOT NULL,
                                                created_At DATETIME(6) NULL,
                                                updated_at DATETIME(6) NULL,
                                                content VARCHAR(255) NOT NULL,
                                                CONSTRAINT FK7h839m3lkvhbyv3bcdv7sm4fj FOREIGN KEY (parent_comment_id)
                                                    REFERENCES comments (id) ON DELETE CASCADE,
                                                CONSTRAINT FKja5i1vdiw6jmv6pno39qlr6od FOREIGN KEY (user_id)
                                                    REFERENCES users (id) ON DELETE CASCADE,
                                                CONSTRAINT FKrcbvcroe2c5uu8ij99lb7vcfd FOREIGN KEY (post_id)
                                                    REFERENCES posts (id) ON DELETE CASCADE
);

-- Create or replace the likes table with ON DELETE CASCADE on user_id and post_id foreign keys
CREATE OR REPLACE TABLE likes (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             post_id INT NULL,
                                             user_id INT NULL,
                                             created_At DATETIME(6) NULL,
                                             CONSTRAINT FKa91dvnfnx92g2c4k7w1aw9ebu FOREIGN KEY (user_id)
                                                 REFERENCES users (id) ON DELETE CASCADE,
                                             CONSTRAINT FKsrb4xtf50k8mp3wd8tf5r6s1y FOREIGN KEY (post_id)
                                                 REFERENCES posts (id) ON DELETE CASCADE
);

-- Create or replace the post_tags table with ON DELETE CASCADE on both id and tag_id foreign keys
CREATE OR REPLACE TABLE post_tags (
                                                 id INT NOT NULL,
                                                 tag_id INT NOT NULL,
                                                 PRIMARY KEY (id, tag_id),
                                                 CONSTRAINT FKeouwh57y9blek9aa4mulekcui FOREIGN KEY (id)
                                                     REFERENCES posts (id) ON DELETE CASCADE,
                                                 CONSTRAINT FKm6cfovkyqvu5rlm6ahdx3eavj FOREIGN KEY (tag_id)
                                                     REFERENCES tags (id) ON DELETE CASCADE
);

-- Create or replace the users_roles table with ON DELETE CASCADE on both role_Id and user_id foreign keys
CREATE OR REPLACE TABLE users_roles (
                                                   role_Id INT NOT NULL,
                                                   user_id INT NOT NULL,
                                                   PRIMARY KEY (role_Id, user_id),
                                                   CONSTRAINT FKd7cnvrrg9medric9br3dty68 FOREIGN KEY (user_id)
                                                       REFERENCES users (id) ON DELETE CASCADE,
                                                   CONSTRAINT FKq5kuha6esfmijvmn2jcsap0bs FOREIGN KEY (role_Id)
                                                       REFERENCES roles (id) ON DELETE CASCADE
);



-- Drop the existing foreign key constraints
ALTER TABLE comments DROP FOREIGN KEY FKrcbvcroe2c5uu8ij99lb7vcfd;
ALTER TABLE posts DROP FOREIGN KEY FKqwy1e63idnvjerwvc47tq3k5;
ALTER TABLE comments DROP FOREIGN KEY FK7h839m3lkvhbyv3bcdv7sm4fj;
ALTER TABLE comments DROP FOREIGN KEY FKja5i1vdiw6jmv6pno39qlr6od;

-- Recreate foreign key constraints with ON DELETE CASCADE

-- Foreign key in comments table referencing posts table
ALTER TABLE comments
    ADD CONSTRAINT FKrcbvcroe2c5uu8ij99lb7vcfd FOREIGN KEY (post_id)
        REFERENCES posts (id) ON DELETE CASCADE;

-- Foreign key in posts table referencing users table
ALTER TABLE posts
    ADD CONSTRAINT FKqwy1e63idnvjerwvc47tq3k5 FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE;

-- Foreign key in comments table referencing parent_comment_id
ALTER TABLE comments
    ADD CONSTRAINT FK7h839m3lkvhbyv3bcdv7sm4fj FOREIGN KEY (parent_comment_id)
        REFERENCES comments (id) ON DELETE CASCADE;

-- Foreign key in comments table referencing users table
ALTER TABLE comments
    ADD CONSTRAINT FKja5i1vdiw6jmv6pno39qlr6od FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE;
