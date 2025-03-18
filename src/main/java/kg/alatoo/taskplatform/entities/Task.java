package kg.alatoo.taskplatform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Task name cannot be empty")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    @Column(unique = true)
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private boolean available;

    @NotNull(message = "Creation date cannot be null")
    @Column(name = "created_day")
    private LocalDate createdDay;

    @Size(max = 100, message = "Solver name must be less than 100 characters")
    private String solver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "level", referencedColumnName = "level")
    private TaskLevel taskLevel;
}