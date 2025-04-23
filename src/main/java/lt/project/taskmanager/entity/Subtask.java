package lt.project.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.project.taskmanager.entity.enums.TaskStatus;

@Entity
@Table(name="subtasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=255, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length=15)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name ="task_id", nullable=false)
    private Task task;
}
