package lt.project.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskType type;
    private Integer sprint;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer userId;

}
