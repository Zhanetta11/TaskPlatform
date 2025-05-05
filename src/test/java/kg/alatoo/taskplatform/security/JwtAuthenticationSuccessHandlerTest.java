package kg.alatoo.taskplatform.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.taskplatform.entities.RefreshToken;
import kg.alatoo.taskplatform.entities.User;
import kg.alatoo.taskplatform.repositories.UserRepository;
import kg.alatoo.taskplatform.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JwtAuthenticationSuccessHandlerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private JwtAuthenticationSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        successHandler = new JwtAuthenticationSuccessHandler(jwtTokenProvider, userRepository, refreshTokenService);
    }

    @Test
    void onAuthenticationSuccess_ShouldWriteTokensToResponse() throws Exception {
        String email = "user@example.com";
        String role = "ROLE_USER";
        String accessToken = "access-token-123";
        String refreshTokenStr = "refresh-token-456";

        User user = new User();
        user.setEmail(email);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenStr);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtTokenProvider.createAccessToken(email, role)).thenReturn(accessToken);
        when(refreshTokenService.createRefreshToken(user)).thenReturn(refreshToken);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                email, null,
                java.util.List.of(new SimpleGrantedAuthority(role))
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);


        successHandler.onAuthenticationSuccess(request, response, authentication);

        String responseContent = outputStream.toString();
        assertTrue(responseContent.contains(accessToken));
        assertTrue(responseContent.contains(refreshTokenStr));

        verify(response).setContentType("application/json");
    }

    @Test
    void onAuthenticationSuccess_UserNotFound_ShouldThrowException() {
        String email = "unknown@example.com";

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                email, null,
                java.util.List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                successHandler.onAuthenticationSuccess(request, response, authentication)
        );

        assertTrue(exception.getMessage().contains("User not found with email"));
    }
}