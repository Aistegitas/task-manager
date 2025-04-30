package lt.project.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateTaskRequest;
import lt.project.taskmanager.dto.GetTaskResponse;
import lt.project.taskmanager.dto.UpdateTaskRequest;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;
import lt.project.taskmanager.converter.TaskConverter;
import lt.project.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter taskConverter;


    @Operation(summary = "Get all tasks or filter by title, type, sprint, status, priority, userId")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @GetMapping
    public List<GetTaskResponse> getAllTasks(@RequestParam(required = false) String title,
                                             @RequestParam(required = false) TaskType type,
                                             @RequestParam(required = false) Integer sprint,
                                             @RequestParam(required = false) TaskStatus status,
                                             @RequestParam(required = false) TaskPriority priority,
                                             @RequestParam(required = false) Integer userId) {
        List<Task> tasks = taskService.filterTasks(title, type, sprint, status, priority, userId);
        return taskConverter.entityListToDto(tasks);
    }

    @Operation(summary = "Add a new task", description = "Creates a new task.")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @PostMapping
    public GetTaskResponse createTask(@RequestBody @Valid CreateTaskRequest request) {
        Task task = taskConverter.toEntity(request);
        return taskConverter.entityToDto(taskService.addTask(task));
    }

    @Operation(summary = "Get a task by ID", description = "Fetches a specific task by ID.")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/{id}")
    public GetTaskResponse getTaskById(@PathVariable Integer id) {
        return taskConverter.entityToDto(taskService.getTaskByIdOrThrow(id));
    }

    @Operation(summary = "Update task", description = "Updates task parameters.")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PatchMapping("/{id}")
    public GetTaskResponse patchTaskById(@PathVariable Integer id, @RequestBody UpdateTaskRequest request) {
        Task existingTask = taskService.getTaskByIdOrThrow(id);
        Task patched = taskConverter.toEntity(existingTask, request);
        return taskConverter.entityToDto(taskService.saveTask(patched));
    }

    @Operation(summary = "Delete a task", description = "Deletes task by ID.")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
    }
}
