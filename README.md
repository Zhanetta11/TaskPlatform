# TaskPlatform

# Task Management Platform

## Overview
The Task Management Platform is a comprehensive and efficient application developed using Spring Boot. It is designed to simplify task management by allowing users to create, update, delete, and view tasks with ease.

## Features
- **Registration:** Manually add new users.
- **Task Management:** Create, update, delete, and view tasks.
- **Task Assignment:** Assign tasks to specific users.
- **Role-Based Access Control:** Supports Admin and User roles for secure data management.
- **Task Status Tracking:** Monitor task progress and completion.
- **Data Persistence:** Uses PostgreSQL and H2 for data storage.
- **RESTful APIs:** Exposes APIs for seamless integration.
- **Swagger Integration:** API documentation using Springfox Swagger.
- **OpenAPI Support:** Enhanced API documentation with SpringDoc OpenAPI.


## Technologies Used
- **Backend:** Spring Boot 3.3.4, Hibernate 6.2.5, Spring Data JPA
- **API Documentation:** Springfox Swagger 2.4.0, SpringDoc OpenAPI 2.0.2
- **Database:** PostgreSQL, H2
- **Build Tool:** Maven 3.8.1
- **Version Control:** Git
- **Validation:** Hibernate Validator 6.2.0
- **Utilities:** Lombok for reducing boilerplate code


## Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL or H2 database
- IDE

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Zhanetta11/TaskPlatform.git
   ```
2. Navigate to the project directory:
   ```bash
   cd TaskPlatform
   ```
3.  Build the project using Maven:
```bash
   mvn clean install
   ```

## Running the Application
Start the application with the following command:
```bash
mvn spring-boot:run
```

Access the application at `http://localhost:8080`.

## API Endpoints
- **User Registration:** `POST /user/register`
- **Create Task:** `POST /task/create/{userEmail}`
- **Update Task:** `PUT /task/updateByName/{name}`
- **Get All Tasks:** `GET /task/getAll`
- **Get Task by ID:** `GET /task/findByName/{name}`
- **API Documentation:** `http://localhost:8080/swagger-ui.html`

## Testing
To run the tests, use the following command:
```bash
mvn test
```