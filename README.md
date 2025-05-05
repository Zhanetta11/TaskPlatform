# TaskPlatform

## Task Management Platform

### Overview

**TaskPlatform** is a robust and efficient task management application developed using **Spring Boot 3**. It streamlines task handling by allowing users to create, update, delete, assign, and monitor tasks, while implementing modern security practices like JWT, OAuth2, and 2FA.

---

## 🔥 Key Features

* **User Registration:** New users can register manually.
* **Task Management:** Full CRUD operations for tasks.
* **Task Assignment:** Assign tasks to specific users.
* **Role-Based Access Control:** Admin and User roles.
* **Task Status Tracking:** Track task progress and completion.
* **Social Login:** Google and GitHub login support.
* **JWT Authentication:** Secure access and refresh tokens.
* **2FA Support:** Two-Factor Authentication using authenticator apps.
* **API Documentation:** Swagger & OpenAPI integration.
* **Database Support:** PostgreSQL and H2.

---

## 🏗 Technologies Used

| Category            | Tools & Frameworks                                      |
| ------------------- | ------------------------------------------------------- |
| Backend             | Spring Boot 3.3.4, Spring Security 6.x, Spring Data JPA |
| ORM                 | Hibernate 6.2.5                                         |
| Validation          | Hibernate Validator 6.2.0                               |
| Authentication      | JWT (io.jsonwebtoken), OAuth2                           |
| API Documentation   | Springfox Swagger 2.4.0, SpringDoc OpenAPI 2.0.2        |
| Database            | PostgreSQL, H2                                          |
| Build Tool          | Maven 3.8.1                                             |
| Code Simplification | Lombok                                                  |
| Version Control     | Git                                                     |
| Language            | Java 21                                                 |

---

## ⚙ Prerequisites

* Java 17 or higher (Java 21 recommended)
* Maven 3.8+
* PostgreSQL or H2 database
* Any IDE (IntelliJ IDEA recommended)

---

## 🚀 Installation

```bash
git clone https://github.com/Zhanetta11/TaskPlatform.git
cd TaskPlatform
mvn clean install
```

---

## ▶ Running the Application

```bash
mvn spring-boot:run
```

Visit:
`http://localhost:8080`

---

## 🔗 API Endpoints Summary

| Method | Endpoint                     | Description                              |
| ------ | ---------------------------- | ---------------------------------------- |
| POST   | /auth/register               | User registration                        |
| POST   | /auth/refresh                | Get new access token using refresh token |
| POST   | /login                       | Form-based login (username/password)     |
| GET    | /oauth2/authorization/google | Google login                             |
| GET    | /oauth2/authorization/github | GitHub login                             |
| POST   | /2fa/enable                  | Enable 2FA for account                   |
| POST   | /2fa/verify                  | Verify 2FA code                          |
| POST   | /task/create/{userEmail}     | Create a new task                        |
| PUT    | /task/updateByName/{name}    | Update task by name                      |
| GET    | /task/getAll                 | Get all tasks                            |
| GET    | /task/findByName/{name}      | Find task by name                        |

---

## 🔐 Authentication & Authorization

### ✅ Authentication Methods:

* **Form-based login** (`/login`)
* **Social login** with Google and GitHub

### ✅ JWT Token Management:

* **Access Token**

   * Lifetime: 1 hour
   * Sent securely to the client in the response body
* **Refresh Token**

   * Lifetime: 7 days
   * Stored securely in the **`refresh_tokens`** table in the database
   * Used for generating new access tokens without re-authentication (`/auth/refresh`)

### ✅ Two-Factor Authentication (2FA):

* **Available for all users**
* Uses authenticator apps (Google Authenticator, Authy, etc.)
* **Endpoints:**

   * Enable 2FA: `POST /2fa/enable?email=your_email`
   * Verify 2FA code: `POST /2fa/verify?email=your_email&code=123456`

### ✅ Password Security:

* Passwords hashed with **BCrypt** before storing.

### ✅ Spring Security Highlights:

* CSRF disabled for API statelessness.
* JWT Authentication Filter (`JwtAuthenticationFilter`) for all secured requests.
* OAuth2 Success Handler (`CustomOAuth2SuccessHandler`) for social login.
* Form Login Success Handler (`JwtAuthenticationSuccessHandler`) for traditional login.
* User details handled through **UserDetailsService**.

---

## 🖥 Swagger / OpenAPI

Swagger UI available at:

```
http://localhost:8080/swagger-ui.html
```

✅ For secured endpoints, click **Authorize** and provide your JWT **Access Token**.

---

## 🛡 Security Best Practices Applied

* ✅ JWT expiration and refresh token strategy implemented.
* ✅ Refresh tokens stored securely in DB.
* ✅ BCrypt password hashing.
* ✅ Two-Factor Authentication (2FA) supported.
* ✅ OAuth2 social logins.
* ✅ Proper Spring Security configuration.
* ✅ All sensitive data transmitted securely.
* ✅ Codebase includes **JavaDoc comments** and inline documentation.

---

## 🧪 Testing

To run all tests:

```bash
mvn test
```

---

## 👩‍💻 Developer Notes

* Use **Bearer Token** in Authorization header to test secured endpoints.
* Use **refresh tokens** to renew access tokens without requiring re-login.
* 2FA can be tested using authenticator apps like Google Authenticator or Authy.

---

## 📌 Final Notes

The **TaskPlatform** backend is built with scalability, security, and extensibility in mind, ensuring a solid foundation for task management with modern authentication and authorization mechanisms.
