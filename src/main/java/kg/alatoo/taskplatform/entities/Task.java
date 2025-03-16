package kg.alatoo.taskplatform.entities;

import jakarta.persistence.*;
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

    @Column(unique = true)
    private String name;
    private String description;
    private boolean available;
    @Column(name = "created_day")
    private LocalDate createdDay;
    private String solver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_email", referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "level", referencedColumnName = "level")
    private TaskLevel taskLevel;
}
