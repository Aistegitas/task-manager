package lt.project.taskmanager.converter;

import lt.project.taskmanager.dto.SubtaskResponse;
import lt.project.taskmanager.entity.Subtask;
import org.springframework.stereotype.Component;

@Component
public class SubtaskConverter {
    public SubtaskResponse toSubtaskResponse(Subtask subtask) {
        return new SubtaskResponse(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getDescription(),
                subtask.getStatus(),
                subtask.getTask().getId()
        );
    }
}
