package lt.project.taskmanager.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.User;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;
import lt.project.taskmanager.repository.TaskRepository;
import lt.project.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final UserService userService;
    private final SubtaskService subtaskService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getAllTasks() {
        log.debug("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Task getTaskByIdOrThrow(Integer id) {
        log.debug("Fetching task with id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task with id: {} not found", id);
                    return new EntityNotFoundException("Task with id: " + id + " not found");
                });
    }

    public void deleteTaskById(Integer id) {
        log.info("Attempting to delete task with id: {}", id);
        if (!taskRepository.existsById(id)) {
            log.warn("Task with id: {} not found", id);
            throw new EntityNotFoundException("Task with id: " + id + " not found");
        }
        taskRepository.deleteById(id);
        log.info("Successfully deleted task with id: {}", id);
    }

    public Task addTask(Task task) {
        Task savedTask = taskRepository.saveAndFlush(task);
        log.info("Successfully added task with id: {}", savedTask.getId());
        return savedTask;
    }

    public Task saveTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    public Task patchTaskById(Integer id, Task taskFromRequest) {
        Task taskFromDb = getTaskByIdOrThrow(id);
        User userFromRequest = taskFromRequest.getUser();

        if (StringUtils.isNotBlank(taskFromRequest.getTitle()) &&
                !taskFromRequest.getTitle().equals(taskFromDb.getTitle())) {
            taskFromDb.setTitle(taskFromRequest.getTitle());
        }
        if (StringUtils.isNotBlank(taskFromRequest.getDescription()) &&
                !taskFromRequest.getDescription().equals(taskFromDb.getDescription())) {
            taskFromDb.setDescription(taskFromRequest.getDescription());
        }
        if (taskFromRequest.getType() != null &&
                !taskFromRequest.getType().equals(taskFromDb.getType())) {
            taskFromDb.setType(taskFromRequest.getType());
        }
        if (taskFromRequest.getSprint() != null &&
                !taskFromRequest.getSprint().equals(taskFromDb.getSprint())) {
            taskFromDb.setSprint(taskFromRequest.getSprint());
        }
        if (taskFromRequest.getStatus() != null &&
                !taskFromRequest.getStatus().equals(taskFromDb.getStatus())) {
            taskFromDb.setStatus(taskFromRequest.getStatus());
        }
        if (taskFromRequest.getPriority() != null &&
                !taskFromRequest.getPriority().equals(taskFromDb.getPriority())) {
            taskFromDb.setPriority(taskFromRequest.getPriority());
        }
        if (userFromRequest.getId() != null &&
                !userFromRequest.getId().equals(taskFromDb.getUser().getId())) {

            User existingUser = userRepository.findById(userFromRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            taskFromDb.setUser(existingUser);
        }

        return saveTask(taskFromDb);
    }


    public void addTestTasks() {
        userService.addTestUsers();

        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            throw new RuntimeException("No users found.");
        }

        List<Task> tasks = new ArrayList<>();

        // --- Task 1 ---
        Task task1 = new Task();
        task1.setTitle("Implement login feature");
        task1.setDescription("Create login functionality with JWT support");
        task1.setType(TaskType.FEATURE);
        task1.setSprint(1);
        task1.setStatus(TaskStatus.TODO);
        task1.setPriority(TaskPriority.HIGH);
        task1.setUser(users.get(0));

        Subtask subtask1 = new Subtask();
        subtask1.setTitle("Design login UI");
        subtask1.setDescription("Mock up login page");
        subtask1.setStatus(TaskStatus.TODO);
        subtask1.setTask(task1);

        Subtask subtask2 = new Subtask();
        subtask2.setTitle("Implement backend");
        subtask2.setDescription("Add endpoint and JWT logic");
        subtask2.setStatus(TaskStatus.TODO);
        subtask2.setTask(task1);

        task1.setSubtasks(List.of(subtask1, subtask2));
        tasks.add(task1);

        // --- Task 2 ---
        Task task2 = new Task();
        task2.setTitle("Fix homepage bug");
        task2.setDescription("Resolve rendering issue on mobile");
        task2.setType(TaskType.BUG);
        task2.setSprint(2);
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setUser(users.get(1));

        Subtask subtask3 = new Subtask();
        subtask3.setTitle("Identify layout bug");
        subtask3.setDescription("Test on different screen sizes");
        subtask3.setStatus(TaskStatus.IN_PROGRESS);
        subtask3.setTask(task2);

        task2.setSubtasks(List.of(subtask3));
        tasks.add(task2);

        // --- Task 3 ---
        Task task3 = new Task();
        task3.setTitle("Database schema update");
        task3.setDescription("Add 'last_login' column to users table");
        task3.setType(TaskType.FEATURE);
        task3.setSprint(2);
        task3.setStatus(TaskStatus.TODO);
        task3.setPriority(TaskPriority.LOW);
        task3.setUser(users.get(2));
        task3.setSubtasks(new ArrayList<>());
        tasks.add(task3);

        // --- Task 4 ---
        Task task4 = new Task();
        task4.setTitle("Optimize images");
        task4.setDescription("Compress and replace heavy assets");
        task4.setType(TaskType.TASK);
        task4.setSprint(3);
        task4.setStatus(TaskStatus.DONE);
        task4.setPriority(TaskPriority.MEDIUM);
        task4.setUser(users.get(3));
        task4.setSubtasks(new ArrayList<>());
        tasks.add(task4);

        // --- Task 5 ---
        Task task5 = new Task();
        task5.setTitle("Write unit tests");
        task5.setDescription("Improve test coverage of service layer");
        task5.setType(TaskType.TASK);
        task5.setSprint(3);
        task5.setStatus(TaskStatus.IN_PROGRESS);
        task5.setPriority(TaskPriority.HIGH);
        task5.setUser(users.get(4));

        Subtask subtask4 = new Subtask();
        subtask4.setTitle("Write test cases for TaskService");
        subtask4.setDescription("Cover all methods including edge cases");
        subtask4.setStatus(TaskStatus.IN_PROGRESS);
        subtask4.setTask(task5);

        task5.setSubtasks(List.of(subtask4));
        tasks.add(task5);

        taskRepository.saveAll(tasks);
        log.info("Saved 5 test tasks with users and subtasks");
    }

}
