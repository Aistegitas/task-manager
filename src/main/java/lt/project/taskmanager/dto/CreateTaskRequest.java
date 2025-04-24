package lt.project.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;
import org.antlr.v4.runtime.misc.NotNull;


@Data
@AllArgsConstructor
public class CreateTaskRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TaskType type;

    @NotNull
    private Integer sprint;

    @NotNull
    private TaskStatus status;

    @NotNull
    private TaskPriority priority;

    @NotNull
    @Positive
    private Integer userId;
}


