package lt.project.taskmanager.mapper;

import lt.project.taskmanager.dto.CreateTaskRequest;
import lt.project.taskmanager.dto.GetTaskResponse;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.dto.UpdateTaskRequest;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.entity.Task;
import lt.project.taskmanager.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public Task toTask(CreateTaskRequest request, User user) {
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

    public Task toTask(Task existingTask, UpdateTaskRequest request, User user) {
        if (request.getTitle() != null) existingTask.setTitle(request.getTitle());
        if (request.getDescription() != null) existingTask.setDescription(request.getDescription());
        if (request.getType() != null) existingTask.setType(request.getType());
        if (request.getSprint() != null) existingTask.setSprint(request.getSprint());
        if (request.getStatus() != null) existingTask.setStatus(request.getStatus());
        if (request.getPriority() != null) existingTask.setPriority(request.getPriority());
        if (user != null) existingTask.setUser(user);
        return existingTask;
    }

    public GetTaskResponse toGetTaskResponse(Task task) {
        List<SubtaskResponse> subtaskResponses = task.getSubtasks() != null
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
                subtaskResponses
        );
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

    public GetTaskResponse toGetTaskResponseWithoutSubtasks(Task task) {
        return new GetTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getType(),
                task.getSprint(),
                task.getStatus(),
                task.getPriority(),
                task.getUser().getId(),
                List.of()
        );
    }
}

