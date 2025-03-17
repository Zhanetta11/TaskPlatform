package kg.alatoo.taskplatform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TaskLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Level name cannot be empty")
    @Size(min = 3, max = 50, message = "Level name must be between 3 and 50 characters")
    @Column(unique = true)
    private String level;

    @NotNull(message = "Points cannot be null")
    @Min(value = 0, message = "Points must be a positive number")
    private int point;

    @OneToMany(mappedBy = "taskLevel", cascade = CascadeType.ALL)
    private List<Task> task;
}