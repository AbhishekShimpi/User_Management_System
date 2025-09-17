# User Management REST API

## Overview

The User Management API is a secure, Spring Boot-based RESTful application. It handles core user management tasks like registration and login using **JWT** for authentication and **Spring Security** for role-based authorization. The project uses **Spring Data JPA** and **MySQL** for data persistence and is built with **Maven**. It has a layered architecture and includes a test suite for quality assurance.

## Features
**User Authentication**: Secure login and registration with a JWT (JSON Web Token) system.

**Role-Based Authorization**: Control access to API endpoints based on user roles (ROLE_USER, ROLE_ADMIN).

**User Data Management**: Standard operations for user entities.

**Password Security**: Stores user passwords securely using BCrypt hashing.

**Global Exception Handling**: Centralizes error management to provide consistent, clean API responses for all errors.

## Prerequisites

Java 8+

Maven

MySQL Database

## Technologies Used
**Spring Boot**: The core framework for building the application.

**Spring Security**: Handles all authentication and authorization logic.

**Spring Data JPA & Hibernate**: For ORM (Object-Relational Mapping) and database interactions.

**JWT (JSON Web Tokens)**: For secure, stateless authentication.

**MySQL**: The relational database used for storing user data.

**Maven**: The build automation tool.

## Setup Instructions
Clone the repository:

```
git clone https://github.com/AbhishekShimpi/User_Management_System.git
cd User_Management_System
```
## Configure the database:
Create a MySQL database named userdb.
Then, update the src/main/resources/application.properties file with your database credentials.
```
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```
## Build and Run:
Use Maven to build and run the application.
```
mvn clean install
mvn spring-boot:run
```
## Access the Application
API: http://localhost:8080   

## Authentication
POST `/api/auth/register`:  Register a new user.

POST `/api/auth/login`:  Authenticate a user and get a JWT token.

## User Management
GET `/api/users/`: Accessible only by ADMIN

GET `/api/users/{id}`: Accessible only by USER or ADMIN 

DELETE `/api/users/{id}`: Only ADMIN can delete.

