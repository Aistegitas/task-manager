
# 🛠️ Task Manager API

A **Spring Boot** RESTful API for managing **Tasks** and **Subtasks**, including:
- CRUD operations
- Filtering tasks by different fields
- Handling relationships between tasks and subtasks
- Validation and error handling
- DTO mapping for clean responses

---

# 📚 Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- Jakarta Validation
- OpenAPI (Swagger annotations)

---

# 🚀 Features

## Tasks
- Create a new task
- Update an existing task
- Delete a task
- Get all tasks tasks with associated subtasks
- Get task by ID
- Filter tasks by title, type, sprint, status, priority, userId

## Subtasks
- Create a new subtask
- Update a subtask 
- Delete a subtask 
- Get all subtasks

## Additional
- Exception handling with detailed error messages
- Clean separation of entities and DTOs
- Validation on incoming request payloads

---

# 📌 API Endpoints

## Task Endpoints
- `GET /api/tasks` — Get all tasks or filter
- `GET /api/tasks/{id}` — Get task by ID
- `POST /api/tasks` — Create new task
- `PATCH /api/tasks/{id}` — Update existing task
- `DELETE /api/tasks/{id}` — Delete task

## Subtask Endpoints
- `GET /api/subtasks` — Get all subtasks
- `POST /api/subtasks` — Create new subtask
- `PATCH /api/subtasks/{id}` — Update subtask 
- `DELETE /api/subtasks/{id}` — Delete subtask

---

# 🧩 Main Components

- **Controllers** — Handle incoming API requests
- **Services** — Business logic for tasks and subtasks
- **Repositories** — Database access layer (Spring Data JPA)
- **Converters** — Convert between entities and DTOs
- **DTOs** — Request and response models
- **Exception Handling** — Global handling of errors

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



