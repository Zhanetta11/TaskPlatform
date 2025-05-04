package kg.alatoo.taskplatform.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.dto.user.UserResponse;
import kg.alatoo.taskplatform.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@SecurityRequirement(name = "basicAuth")
@Validated
public class UserController {
    private final UserDetailsService service;

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable @Email(message = "Invalid email format") String email) {
        return service.findByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable @Email(message = "Invalid email format") String email,
                              @RequestBody @Valid UserRequest userRequest) {
        service.updateByEmail(email, userRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable @Email(message = "Invalid email format") String email) {
        service.deleteByEmail(email);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserRequest userRequest) {
        service.register(userRequest);
    }
}