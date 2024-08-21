# ForumProject

## Overview
ForumProject is a web application built using Java, Spring Boot, and Thymeleaf. It allows users to create, view, and manage posts and comments. The application also includes an admin portal for managing users and their posts.

## Features
- User authentication and authorization
- Create, read, update, and delete posts
- Comment on posts and comments
- Filter posts by title, content, username, and tag name
- Admin portal for managing users and their posts
- Role-based access control
- Email notifications for registration and post comments
- API available for external integrations
- Session security for MVC
- JWT security for API


## Technologies Used
- Java
- Spring Boot
- Thymeleaf
- Gradle
- MySQL/MariaDB

## Getting Started

### Prerequisites
- Java 11 or higher
- Gradle
- A SQL database (e.g., MySQL, PostgreSQL)

### Installation
Clone the repository:
```sh
git clone https://github.com/your-username/ForumProject.git
cd ForumProject
```
1. Environment Variables
   You need to set up the following environment variables for database and Cloudinary configuration:


 - `mySQLuser`: Database URL
 - `mySQLuser`: your database username
 - `mySQLpassword`: your database password
 - 
   Example:
   mySQLuser=root;mySQLpassword=12345;dbURL=jdbc:mariadb://localhost:3306/forum_system?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC&sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false;

 - `API_KEY`: Cloudinary API key
 - `API_SECRET`: Cloudinary API secret
 - `CLOUD_NAME`: Cloudinary cloud name
   Example:
   API_KEY=794954315984349;API_SECRET=-CeCea1-2ZoUs8KmT_4BKIVRW2s;CLOUD_NAME=dtwfzrl2v;

 - `MAILUSER`: Email address for sending emails
 - `MAILPASS`: Email password for sending emails
   MAILUSER=forumdd01@gmail.com;MAILPASS=hohq ofox lpvy kivi

2. Database Setup
   2.1 Hibernate Configuration
   In the HibernateConfig class:
 - Change the `hibernate.hbm2ddl.auto` property to `create` to create the database schema.
 - Change the `hibernate.hbm2ddl.auto` property to `update` to update the database schema.
   2.2 Initial Data Population
   To auto-populate the database with initial data:
   In application.properties, set the `spring.jpa.hibernate.ddl-auto` property to `create` or `update`.
   Once the application is running, the database will be populated with initial data and the property should be set back to `never`.

3. Running SQL Scripts Manually (Optional)
   SQL scripts are located in the src/main/resources folder.
   You can run these scripts manually if needed.
   Note: Ensure that the password is hashed in the user table if you are manually inserting data.

3. Build the project:
    ```sh
    gradle build
    ```

4. Run the application:
    ```sh
    gradle bootRun
    ```

### Usage
- Access the application at `http://localhost:8080`.
- Register a new user or log in with an existing account.
- Create, view, and manage posts and comments.
- Access the admin portal at `http://localhost:8080/admin` (requires admin privileges).

## Project Structure
- `src/main/java/com/example/ForumProject`: Contains the Java source code.
- `src/main/resources`: Contains the application properties and Thymeleaf templates.
- `src/test/java/com/example/ForumProject`: Contains the test cases.

## Contributing
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact
For any inquiries or issues, please contact `your-email@example.com`.