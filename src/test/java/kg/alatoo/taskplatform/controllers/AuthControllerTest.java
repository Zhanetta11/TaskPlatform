package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.repositories.RefreshTokenRepository;
import kg.alatoo.taskplatform.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;
    private JwtTokenProvider jwtTokenProvider;
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = mock(JwtTokenProvider.class);
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        authController = new AuthController(jwtTokenProvider, refreshTokenRepository);
    }

    @Test
    void refreshToken_ShouldReturnNewAccessToken_WhenValidRefreshTokenProvided() {
        String refreshTokenStr = "valid-refresh-token";
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole("USER");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenStr);
        refreshToken.setUser(user);

        when(refreshTokenRepository.findByToken(refreshTokenStr)).thenReturn(Optional.of(refreshToken));
        when(jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole())).thenReturn("new-access-token");

        ResponseEntity<?> response = authController.refreshToken(Map.of("refresh_token", refreshTokenStr));

        assertEquals(200, response.getStatusCodeValue());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertNotNull(body);
        assertEquals("new-access-token", body.get("access_token"));

        verify(refreshTokenRepository, times(1)).findByToken(refreshTokenStr);
        verify(jwtTokenProvider, times(1)).createAccessToken(user.getEmail(), user.getRole());
    }

    @Test
    void refreshToken_ShouldThrowException_WhenRefreshTokenIsInvalid() {
        String invalidRefreshTokenStr = "invalid-token";

        when(refreshTokenRepository.findByToken(invalidRefreshTokenStr)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.refreshToken(Map.of("refresh_token", invalidRefreshTokenStr));
        });

        assertEquals("Refresh token is invalid or expired", exception.getMessage());
        verify(refreshTokenRepository, times(1)).findByToken(invalidRefreshTokenStr);
    }
}