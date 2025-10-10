# Post API

A RESTful API for managing posts, built with **Java Spring Boot**, **MySQL**, and **Docker**.  
Provides full CRUD functionality and demonstrates a clean, layered backend architecture.

---

## Features

- Full CRUD for posts (create, read, update, delete)
- Persistent storage with MySQL
- Containerized with Docker & Docker Compose
- Clean separation of layers (Controller, Service, Repository, Entity)
- Ready to deploy and extend

---

## Tech Stack

- **Java 17+**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **MySQL**
- **Maven**
- **Docker & Docker Compose**

---

## Getting Started

### Prerequisites
- Java JDK 17 or newer
- Maven
- Docker & Docker Compose
- Git

### Clone & Run
```bash
git clone https://github.com/s1zyy/post-api.git
cd post-api
```
Run with Docker
```bash
docker-compose up --build
```
This will start:

MySQL database container

Spring Boot API container

The API will be available at:
```bash
http://localhost:8080
```

API Endpoints

| Method | Endpoint      | Description        |
|:------:|:-------------:|:------------------:|
| GET    | `/posts`      | Fetch all posts    |
| GET    | `/posts/{id}` | Fetch a post by ID |
| POST   | `/posts`      | Create a new post  |
| PUT    | `/posts/{id}` | Update a post      |
| DELETE | `/posts/{id}` | Delete a post      |
| POST   | `/users`         | Create a new user               |

Example JSON
```JSON
{
  "id": 1,
  "title": "Hello World",
  "content": "This is my first post",
  "createdAt": "2025-10-04T12:34:56",
  "updatedAt": "2025-10-04T12:34:56"
}
```

Project Structure

```bash
src
 └── main
      ├── java/com/example/postapi
      │    ├── controller   # REST controllers
      │    ├── service      # Business logic
      │    ├── repository   # JPA repositories
      │    └── model        # Post entity
      └── resources
           └── application.yaml
```

Future Improvements

Swagger/OpenAPI documentation

Global exception handling

Author

Developed by Vladyslav Savkiv

Backend developer passionate about APIs, bots, and clean architecture.