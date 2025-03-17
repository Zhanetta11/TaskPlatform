package kg.alatoo.taskplatform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users_db")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    private String role;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;
}