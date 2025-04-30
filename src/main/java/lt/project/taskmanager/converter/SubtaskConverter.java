package lt.project.taskmanager.converter;

import lt.project.taskmanager.dto.CreateSubtaskRequest;
import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.entity.Subtask;
import lt.project.taskmanager.entity.Task;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubtaskConverter {

    public SubtaskResponse entityToDto(Subtask subtask) {
        return new SubtaskResponse(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getDescription(),
                subtask.getStatus(),
                subtask.getTask().getId()
        );
    }

    public List<SubtaskResponse> entityListToDto(List<Subtask> subtasks) {
        return subtasks.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public Subtask toEntity(CreateSubtaskRequest request, Task task) {
        Subtask subtask = new Subtask();
        subtask.setTitle(request.getTitle());
        subtask.setDescription(request.getDescription());
        subtask.setStatus(request.getStatus());
        subtask.setTask(task);
        return subtask;
    }
}
