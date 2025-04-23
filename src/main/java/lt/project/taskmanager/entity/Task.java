package lt.project.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.project.taskmanager.entity.enums.TaskPriority;
import lt.project.taskmanager.entity.enums.TaskStatus;
import lt.project.taskmanager.entity.enums.TaskType;

import java.util.List;

@Entity
@Table(name="tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private TaskType type;

    @Column
    private Integer sprint;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Subtask> subtasks;
}
