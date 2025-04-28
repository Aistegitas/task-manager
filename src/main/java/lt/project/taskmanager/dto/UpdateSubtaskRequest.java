package lt.project.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.project.taskmanager.entity.enums.TaskStatus;

@Data
@AllArgsConstructor
public class UpdateSubtaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
}
