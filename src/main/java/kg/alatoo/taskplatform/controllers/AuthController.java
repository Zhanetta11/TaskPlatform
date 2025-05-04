package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestParam String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtTokenProvider.getEmail(refreshToken);

        String role;
        try {
            role = jwtTokenProvider.getRole(refreshToken);
        } catch (Exception e) {
            role = "USER";
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(email, role);

        return Map.of("accessToken", newAccessToken);
    }
}