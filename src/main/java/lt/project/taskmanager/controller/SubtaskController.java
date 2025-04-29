package lt.project.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateSubtaskRequest;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.dto.UpdateSubtaskRequest;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.mapper.SubtaskMapper;
import lt.project.taskmanager.service.SubtaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subtasks")
@RequiredArgsConstructor
public class SubtaskController {
    private final SubtaskService subtaskService;
    private final SubtaskMapper subtaskMapper;

    @Operation(summary = "Get all subtasks")
    @ApiResponse(responseCode = "201", description = "Subtask created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @GetMapping
    public ResponseEntity<List<SubtaskResponse>> getAllSubtasks() {
        List<SubtaskResponse> subtasks = subtaskService.getAllSubtasks()
                .stream()
                .map(subtaskMapper::toSubtaskResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subtasks);
    }

    @Operation(summary = "Add a new subtask", description = "Creates a new subtask.")
    @ApiResponse(responseCode = "201", description = "Subtask created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @PostMapping
    public ResponseEntity<SubtaskResponse> createSubtask(@RequestBody @Valid CreateSubtaskRequest request) {
        Subtask subtask = subtaskService.addSubtask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subtaskMapper.toSubtaskResponse(subtask));
    }

    @Operation(summary = "Update subtask", description = "Updates subtask parameters.")
    @ApiResponse(responseCode = "200", description = "Subtask updated successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @PatchMapping("/{id}")
    public ResponseEntity<SubtaskResponse> updateSubtask(@PathVariable Integer id,
                                                         @RequestBody @Valid UpdateSubtaskRequest request) {
        Subtask updatedSubtask = subtaskService.updateSubtask(id, request);
        return ResponseEntity.ok(subtaskMapper.toSubtaskResponse(updatedSubtask));
    }

    @Operation(summary = "Delete a subtask", description = "Deletes subtask by ID.")
    @ApiResponse(responseCode = "204", description = "Subtask deleted successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Integer id) {
        subtaskService.deleteSubtask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get subtask by ID", description = "Fetches a specific subtask by ID.")
    @ApiResponse(responseCode = "200", description = "Subtask retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Subtask not found")
    @GetMapping("/{id}")
    public ResponseEntity<SubtaskResponse> getSubtask(@PathVariable Integer id) {
        Subtask subtask = subtaskService.getSubtaskByIdOrThrow(id);
        return ResponseEntity.ok(subtaskMapper.toSubtaskResponse(subtask));
    }
}
