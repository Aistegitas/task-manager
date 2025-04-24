package lt.project.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.mapper.SubtaskMapper;
import lt.project.taskmanager.service.SubtaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
