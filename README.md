# ☕ Post API: Secure RESTful Service with Spring Boot & Docker

**Post API** is a robust, production-ready backend service built on **Java** and **Spring Boot**. It provides a secure **RESTful API** for managing user posts, demonstrating proficiency in database integration, clean architecture, and containerization.

## 🛠️ Technology Stack

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Containerization-Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

---

## ✨ Key Features & Architecture

This project emphasizes industry best practices for scalable backend development:

* **Full CRUD Functionality:** Complete API support for creating, reading, updating, and deleting posts.
* **Layered Architecture:** Strict separation of concerns (Controller, Service, Repository) ensures maintainability and adherence to the **Service Layer Pattern**.
* **Persistent Storage:** Uses **Spring Data JPA** with a **MySQL** database for reliable data persistence.
* **Containerized Environment:** Packaged with **Docker** and managed by **Docker Compose** for quick, reproducible setup.
* **Security (Planned):** Designed with modularity in mind, ready for immediate integration of **JWT Authentication** (See Future Improvements).

---

## 🚀 Getting Started

The quickest way to run this application is by using Docker Compose, which automatically sets up both the Spring Boot service and the MySQL database.

### Prerequisites

* Java JDK 17 or newer
* Maven (or just the included `mvnw` wrapper)
* **Docker & Docker Compose**
* Git

### Clone & Run

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/s1zyy/post-api.git](https://github.com/s1zyy/post-api.git)
    cd post-api
    ```

2.  **Start the Service & Database (Docker Compose):**
    ```bash
    docker-compose up --build
    ```
    *The API will be available at:* `http://localhost:8080`

---

## 🌐 API Endpoints

The API is structured around the `/api/v1` base path (if configured in `application.yaml`).

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `GET` | `/posts` | Fetch all posts | `200 OK` |
| `GET` | `/posts/{id}` | Fetch a post by its ID | `200 OK` |
| `POST` | `/posts` | Create a new post | `201 CREATED` |
| `PUT` | `/posts/{id}` | Update an existing post | `200 OK` |
| `DELETE` | `/posts/{id}` | Delete a post by its ID | `204 NO CONTENT` |

### Example Request Body (POST /posts)

```json
{
  "title": "My First Post Title",
  "content": "Detailed content of the first post."
}
```
### 📂 Project Structure

A simple, clear structure demonstrating separation of concerns:

```
src
 └── main
      ├── java/com/example/postapi
      │    ├── controller   # Handles incoming HTTP requests and routing
      │    ├── service      # Contains all core business logic and transactions
      │    ├── repository   # Manages database interactions (Spring Data JPA)
      │    └── model        # JPA entities and DTOs
      └── resources
           └── application.yaml # Database configuration and Spring settings
```

## 👤 Author & Related Projects

**Vladyslav Savkiv** – Full-Stack (Java/iOS) Developer

* [LinkedIn Profile](https://www.linkedin.com/in/vladyslav-savkiv/)
* [GitHub Profile](https://github.com/s1zyy)
* Related Client Project: [iOS PostSwiftApp](https://github.com/s1zyy/postSwiftApp)