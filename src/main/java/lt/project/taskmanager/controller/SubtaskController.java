package lt.project.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateSubtaskRequest;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.dto.UpdateSubtaskRequest;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.converter.SubtaskConverter;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.service.SubtaskService;
import lt.project.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
@RequiredArgsConstructor
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final TaskService taskService;
    private final SubtaskConverter converter;

    @Operation(summary = "Get all subtasks")
    @ApiResponse(responseCode = "201", description = "Subtask created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @GetMapping
    public List<SubtaskResponse> getAllSubtasks() {
        return converter.entityListToDto(subtaskService.getAllSubtasks());
    }

    @Operation(summary = "Add a new subtask", description = "Creates a new subtask.")
    @ApiResponse(responseCode = "201", description = "Subtask created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @PostMapping
    public SubtaskResponse createSubtask(@RequestBody @Valid CreateSubtaskRequest request) {
        Task task = taskService.getTaskByIdOrThrow(request.getTaskId());
        Subtask subtask = converter.toEntity(request, task);
        Subtask saved = subtaskService.addSubtask(subtask);
        return converter.entityToDto(saved);
    }

    @Operation(summary = "Get subtask by ID", description = "Fetches a specific subtask by ID.")
    @ApiResponse(responseCode = "200", description = "Subtask retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @GetMapping("/{id}")
    public SubtaskResponse getSubtaskById(@PathVariable Integer id) {
        return converter.entityToDto(subtaskService.getSubtaskByIdOrThrow(id));
    }

    @Operation(summary = "Update subtask", description = "Updates subtask parameters.")
    @ApiResponse(responseCode = "200", description = "Subtask updated successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @PatchMapping("/{id}")
    public SubtaskResponse updateSubtask(@PathVariable Integer id, @RequestBody UpdateSubtaskRequest request) {
        Subtask updated = subtaskService.updateSubtask(id, request);
        return converter.entityToDto(updated);
    }

    @Operation(summary = "Delete a subtask", description = "Deletes subtask by ID.")
    @ApiResponse(responseCode = "204", description = "Subtask deleted successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @DeleteMapping("/{id}")
    public void deleteSubtask(@PathVariable Integer id) {
        subtaskService.deleteSubtask(id);
    }
}
