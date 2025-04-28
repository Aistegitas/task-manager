package lt.project.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateTaskRequest;
import lt.project.taskmanager.dto.GetTaskResponse;
import lt.project.taskmanager.dto.UpdateTaskRequest;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.User;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;
import lt.project.taskmanager.exception.ResourceNotFoundException;
import lt.project.taskmanager.mapper.TaskMapper;
import lt.project.taskmanager.service.TaskService;
import lt.project.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @GetMapping
    @Operation(summary = "Get all tasks or filter by title, type, sprint, status, priority, userId")
    @Parameters({
            @Parameter(name = "title", description = "Filter by task title", required = false),
            @Parameter(name = "type", description = "Filter by task type", required = false),
            @Parameter(name = "sprint", description = "Filter by sprint", required = false),
            @Parameter(name = "status", description = "Filter by task status", required = false),
            @Parameter(name = "priority", description = "Filter by task priority", required = false),
            @Parameter(name = "userId", description = "Filter by user ID", required = false)
    })
    public ResponseEntity<List<GetTaskResponse>> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) TaskType type,
            @RequestParam(required = false) Integer sprint,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Integer userId
    ) {
        List<Task> tasks = taskService.filterTasks(title, type, sprint, status, priority, userId);

        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("No tasks found matching the criteria.");
        }

        List<GetTaskResponse> response = tasks.stream()
                .map(taskMapper::toGetTaskResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/with-subtasks")
    public List<GetTaskResponse> getAllTasksWithSubtasks() {
        return taskService.getAllTasks().stream()
                .map(taskMapper::toGetTaskResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> getTaskById(@PathVariable Integer id) {
        Task task = taskService.getTaskByIdOrThrow(id);
        return ResponseEntity.ok(taskMapper.toGetTaskResponse(task));
    }

    @PostMapping
    public ResponseEntity<GetTaskResponse> createTask(@RequestBody @Valid CreateTaskRequest request) {
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found"));

        Task task = taskMapper.toTask(request, user);
        Task savedTask = taskService.addTask(task);

        return ResponseEntity.ok(taskMapper.toGetTaskResponse(savedTask));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetTaskResponse> updateTask(@PathVariable Integer id,
                                                      @RequestBody UpdateTaskRequest request) {
        Task existingTask = taskService.getTaskByIdOrThrow(id);

        User user = request.getUserId() != null
                ? userService.getUserById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found"))
                : existingTask.getUser();

        Task updatedTask = taskMapper.toTask(existingTask, request, user);
        updatedTask = taskService.saveTask(updatedTask);

        return ResponseEntity.ok(taskMapper.toGetTaskResponse(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
