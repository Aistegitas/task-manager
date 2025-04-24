package lt.project.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;

import java.util.List;

@Data
@AllArgsConstructor
public class GetTaskResponse {
    private Integer id;
    private String title;
    private String description;
    private TaskType type;
    private Integer sprint;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer userId;

    private List<SubtaskResponse> subtasks;
}
