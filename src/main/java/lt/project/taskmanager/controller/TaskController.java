package lt.project.taskmanager.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateTaskRequest;
import lt.project.taskmanager.dto.GetTaskResponse;
import lt.project.taskmanager.dto.UpdateTaskRequest;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.User;
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
    public ResponseEntity<List<GetTaskResponse>> getAllTasks() {
        List<GetTaskResponse> tasks = taskService.getAllTasks()
                .stream()
                .map(taskMapper::toGetTaskResponseWithoutSubtasks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
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
