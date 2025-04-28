package lt.project.taskmanager.controller;

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

    @GetMapping
    public ResponseEntity<List<SubtaskResponse>> getAllSubtasks() {
        List<SubtaskResponse> subtasks = subtaskService.getAllSubtasks()
                .stream()
                .map(subtaskMapper::toSubtaskResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subtasks);
    }

    @PostMapping
    public ResponseEntity<SubtaskResponse> createSubtask(@RequestBody @Valid CreateSubtaskRequest request) {
        Subtask subtask = subtaskService.addSubtask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subtaskMapper.toSubtaskResponse(subtask));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubtaskResponse> updateSubtask(@PathVariable Integer id,
                                                         @RequestBody @Valid UpdateSubtaskRequest request) {
        Subtask updatedSubtask = subtaskService.updateSubtask(id, request);
        return ResponseEntity.ok(subtaskMapper.toSubtaskResponse(updatedSubtask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Integer id) {
        subtaskService.deleteSubtask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubtaskResponse> getSubtask(@PathVariable Integer id) {
        Subtask subtask = subtaskService.getSubtaskByIdOrThrow(id);
        return ResponseEntity.ok(subtaskMapper.toSubtaskResponse(subtask));
    }
}
