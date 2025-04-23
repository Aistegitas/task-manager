package lt.project.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length=255, nullable=false)
    private String firstName;

    @Column(name = "last_name", length=255, nullable=false)
    private String lastName;

    @Column(length=255, nullable=false, unique = true)
    private String email;
}
