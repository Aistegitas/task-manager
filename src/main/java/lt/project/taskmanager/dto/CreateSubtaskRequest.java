package lt.project.taskmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lt.project.taskmanager.entity.enums.TaskStatus;

@Data
@AllArgsConstructor
public class CreateSubtaskRequest {
    @NotNull
    private String title;

    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Integer taskId;

}

