package kg.alatoo.taskplatform.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.repositories.RefreshTokenRepository;
import kg.alatoo.taskplatform.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {

        String refreshTokenStr = request.get("refresh_token");

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token is invalid or expired"));

        User user = refreshToken.getUser();

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(Map.of("access_token", newAccessToken));
    }
}