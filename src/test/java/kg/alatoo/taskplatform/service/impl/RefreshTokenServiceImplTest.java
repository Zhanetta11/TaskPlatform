package kg.alatoo.taskplatform.service.impl;

import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.repositories.RefreshTokenRepository;
import kg.alatoo.taskplatform.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenDurationMs", 60000L);
    }

    @Test
    void createRefreshToken() {
        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(testUser);

        assertThat(refreshToken).isNotNull();
        assertThat(refreshToken.getToken()).isNotEmpty();
        assertThat(refreshToken.getUser()).isEqualTo(testUser);

        verify(refreshTokenRepository).deleteByUser(testUser);
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    void validateRefreshToken_ShouldReturnTrue_WhenValid() {
        String tokenStr = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(tokenStr)
                .user(testUser)
                .expiryDate(Instant.now().plusSeconds(60))
                .build();

        when(refreshTokenRepository.findByToken(tokenStr)).thenReturn(Optional.of(refreshToken));

        boolean result = refreshTokenService.validateRefreshToken(tokenStr);

        assertThat(result).isTrue();
    }

    @Test
    void validateRefreshToken_ShouldReturnFalse_WhenExpired() {
        String tokenStr = UUID.randomUUID().toString();
        RefreshToken expiredToken = RefreshToken.builder()
                .token(tokenStr)
                .user(testUser)
                .expiryDate(Instant.now().minusSeconds(60))
                .build();

        when(refreshTokenRepository.findByToken(tokenStr)).thenReturn(Optional.of(expiredToken));

        boolean result = refreshTokenService.validateRefreshToken(tokenStr);

        assertThat(result).isFalse();
    }

    @Test
    void validateRefreshToken_ShouldReturnFalse_WhenNotFound() {
        String tokenStr = UUID.randomUUID().toString();
        when(refreshTokenRepository.findByToken(tokenStr)).thenReturn(Optional.empty());

        boolean result = refreshTokenService.validateRefreshToken(tokenStr);

        assertThat(result).isFalse();
    }

    @Test
    void deleteByUser() {
        refreshTokenService.deleteByUser(testUser);

        verify(refreshTokenRepository).deleteByUser(testUser);
    }
}