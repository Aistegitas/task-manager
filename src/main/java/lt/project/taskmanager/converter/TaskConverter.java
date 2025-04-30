package lt.project.taskmanager.converter;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.dto.CreateTaskRequest;
import lt.project.taskmanager.dto.GetTaskResponse;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.dto.UpdateTaskRequest;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.User;
import lt.project.taskmanager.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskConverter {

    private final UserService userService;

    public Task toEntity(CreateTaskRequest request) {
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setType(request.getType());
        task.setSprint(request.getSprint());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setUser(user);
        return task;
    }

    public Task toEntity(Task existingTask, UpdateTaskRequest request) {
        if (request.getTitle() != null) existingTask.setTitle(request.getTitle());
        if (request.getDescription() != null) existingTask.setDescription(request.getDescription());
        if (request.getType() != null) existingTask.setType(request.getType());
        if (request.getSprint() != null) existingTask.setSprint(request.getSprint());
        if (request.getStatus() != null) existingTask.setStatus(request.getStatus());
        if (request.getPriority() != null) existingTask.setPriority(request.getPriority());

        if (request.getUserId() != null) {
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found"));
            existingTask.setUser(user);
        }

        return existingTask;
    }

    public GetTaskResponse entityToDto(Task task) {
        List<SubtaskResponse> subtasks = task.getSubtasks() != null
                ? task.getSubtasks().stream()
                .map(this::toSubtaskResponse)
                .collect(Collectors.toList())
                : List.of();

        return new GetTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getType(),
                task.getSprint(),
                task.getStatus(),
                task.getPriority(),
                task.getUser().getId(),
                subtasks
        );
    }

    public List<GetTaskResponse> entityListToDto(List<Task> tasks) {
        return tasks.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private SubtaskResponse toSubtaskResponse(Subtask subtask) {
        return new SubtaskResponse(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getDescription(),
                subtask.getStatus(),
                subtask.getTask().getId()
        );
    }
}

