Project Description
This is a full-stack Blog Application built using Spring Boot. It allows users to register, log in, and perform blog operations like creating, updating, and deleting posts. The application implements Role-Based Authentication (Admin/User) using JWT (JSON Web Tokens) and Spring Security.

Tech Stack

Backend:

Java 24

Spring Boot

Spring Security

JWT Authentication

Spring Data JPA

Hibernate

MySQL Database


📁 Features----------------------->

🔐 User Registration with password encryption

🔐 JWT-based Login & Authentication

👥 Role-Based Authorization (ROLE_ADMIN / ROLE_USER)

✍️ Create, Update, Delete Blog Posts

✍️ Create, Update, Delete comment to Posts

🔎 Get all blogs / blogs by user / single blog

📄 Global Exception Handling

🧱 Database Schema

User Table:

id (UUID) | username  |password | email | roles (Many-to-Many with Role table)


Role Table:

id | name (e.g. ROLE_USER, ROLE_ADMIN)


Post Table:

id | title | content | createdAt | updatedAt



Comment Table: 

id | titile | content | authore | createdTime |  updatedTime | comments(OneToMany)



user_id (Foreign Key)

🚀 API Endpoints

🔐 Authentication

POST /auth/register → Register user and save to dataBase

POST /auth/login → Login and get JWT token

GET /auth/current →  to check currnet user login

📑 Posts

POST /post/create → Create new post

PUT /post/{id} → Update post

DELETE /post/{id} → Delete post

GET /post/getAll → Get all posts

GET /post/getSingle/{pstIdId} → Get simgle  post

📑 Comment

POST /comment/create → Create new comment to post

PUT /comment/update{id} → Update commnet

DELETE /commnet/delete/{id} → Delete commnet

GET /comment/getSingleComment/{commentId} → Get single  comment

GET /comment/readComment/{pstIdId} → Get  single  post to all  comment




