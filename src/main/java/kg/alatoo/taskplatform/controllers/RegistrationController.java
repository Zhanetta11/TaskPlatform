package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.dto.user.UserRequest;
import kg.alatoo.taskplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {

        userService.register(userRequest);

        return ResponseEntity.ok("User registered successfully");
    }
}