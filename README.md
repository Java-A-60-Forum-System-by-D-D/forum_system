This README provides information on setting `pathVariables` in `application.properties` as well as other basic aspects of our application.
# Forum System API

## Description

It is a RESTful API for a social media platform that allows users to create and manage posts, comments and tags. In addition, it provides the ability to manage user profiles and authentication.

## Contents

- [Features](#features)
- [Requirements](#requirements)
- [Setup](#setup)
- [Use](#use)
- [API Documentation](#api-documentation)
- [License](#license)

## Functionalities

- **Posts**: Create, read, update and delete posts.
- **Comments**: Add, read and manage comments.
- **Tags**: Create, update and manage tags.
- **User Profiles**: Registration, login and update of profiles.
- **Authentication**: Login and registration with JWT token.

## Requirements

- Java 17 or later
- Maven or Gradle (depending on the build tool used)
- Spring Boot
- Database (e.g. PostgreSQL, MySQL)

## Setup
1.Environment Variables
- To begin with, your local settings for the Database should be added to EnvironmentVariables as they are set in application.properties
- Example:
-> mySQLuser=root; mySQLpassword=12345;
 The next step is Cloudinary variables:
 API_KEY=794954315984349;API_SECRET=-CeCea1-2ZoUs8KmT_4BKIVRW2s;CLOUD_NAME=dtwfzrl2v
2. Database
2.1 HibetnateConfig
- In HibernateConfig -> hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update"); -> must be set to create or create-drop to create the database if you don't want to run the script manually. It can then be set back to "update" again.
2.2 Filling the base
- In application.properties setting spring.sql.init.mode=always, after filling should be set to never.

!!!For now, it's fine to use insert_into_all_tables with "hashed_password" for all users
!!! If you want to manually run the scripts they are located in the resources folder.
!!! Functionality with Tags has not yet been tested
- 
  
