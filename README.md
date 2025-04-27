
# 🛠️ Task Manager API

A **Spring Boot** RESTful API for managing **Tasks** and **Subtasks**, including:
- CRUD operations
- Filtering tasks by different fields
- Handling relationships between tasks and subtasks
- Validation and error handling
- Proper DTO mapping for clean responses

---

## 📚 Features

- **Task Management**  
  Create, update (PATCH), delete, view single or multiple tasks.

- **Subtask Management**  
  List all subtasks or subtasks belonging to a specific task.

- **Filtering**  
  Filter tasks by:
  - Title
  - Type
  - Sprint
  - Status
  - Priority
  - User ID

- **DTO Usage**  
  Clean request and response models using DTOs and Lombok.

- **Validation**  
  Automatic request validation (e.g., `@NotBlank`, `@Positive`, `@NotNull`).

- **Error Handling**  
  Friendly and clear error messages for missing entities or bad requests.

---

## 📦 Technologies Used

- Java 17+
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- MySQL

---

## 🏗️ Project Structure

| Layer        | Purpose                                  |
|--------------|------------------------------------------|
| `entity`     | JPA Entities (Task, Subtask, User)        |
| `dto`        | Data Transfer Objects                    |
| `controller` | REST API endpoints (TaskController, SubtaskController) |
| `service`    | Business logic (TaskService, SubtaskService) |
| `mapper`     | Converts between Entities and DTOs       |
| `repository` | Spring Data JPA Repositories             |

---

## 🚀 API Endpoints

### Task Endpoints

| Method | URL | Description |
|-------|-----|-------------|
| `GET` | `/api/tasks` | Get all tasks (with/without subtasks) |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `POST` | `/api/tasks` | Create a new task |
| `PATCH` | `/api/tasks/{id}` | Update a task partially |
| `DELETE` | `/api/tasks/{id}` | Delete a task |
| `GET` | `/api/tasks/filter` | Filter tasks by fields |

### Subtask Endpoints

| Method | URL | Description |
|-------|-----|-------------|
| `GET` | `/api/subtasks` | Get all subtasks |
| `GET` | `/api/subtasks/task/{taskId}` | Get subtasks by task ID |

---

---

## ⚙️ Run Locally

Follow these steps to run the project on your local machine:

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/task-manager.git
   cd task-manager
   ```

2. **Open the project**  

   Open the project folder in your IDE (such as IntelliJ IDEA ).

3. **Check your environment**  
   - Make sure you have **Java 17** (or higher) installed.
   - Make sure you have **Maven** installed.

4. **Configure the Database**  

   If you want to connect to **MySQL** database, update `application.properties` accordingly.

5. **Install project dependencies**
   ```bash
   ./mvnw clean install
   ```
   or if you are on Windows:
   ```bash
   mvnw.cmd clean install
   ```

6. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   or in your IDE, simply run the `TaskManagerApplication` class.

7. **Access the API**
     
     ```
     http://localhost:8080/api/tasks
     ```

8. **(Optional) Test with Postman**  
   Use [Postman](https://www.postman.com/) to test the API endpoints

---


