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

Frontend (Optional/Future):

HTML, CSS, JS (or React/Angular)

📁 Features

🔐 User Registration with password encryption

🔐 JWT-based Login & Authentication

👥 Role-Based Authorization (ROLE_ADMIN / ROLE_USER)

✍️ Create, Update, Delete Blog Posts

🔎 Get all blogs / blogs by user / single blog

📄 Global Exception Handling

🧱 Database Schema

User Table:

id (UUID)

username

email

password

roles (Many-to-Many with Role table)

Role Table:

id

name (e.g. ROLE_USER, ROLE_ADMIN)

Post Table:

id

title

content

imageName

createdAt

updatedAt

user_id (Foreign Key)

🚀 API Endpoints

🔐 Authentication

POST /api/auth/register → Register user

POST /api/auth/login → Login and get JWT token

👥 Users

GET /api/users/ → Get all users (Admin only)

GET /api/users/{id} → Get user by ID

📑 Posts

POST /api/posts/ → Create new post

PUT /api/posts/{id} → Update post

DELETE /api/posts/{id} → Delete post

GET /api/posts/ → Get all posts

GET /api/posts/user/{userId} → Get posts by user

