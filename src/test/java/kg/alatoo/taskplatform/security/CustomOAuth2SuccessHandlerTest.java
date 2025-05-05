package kg.alatoo.taskplatform.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CustomOAuth2SuccessHandlerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    private CustomOAuth2SuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        successHandler = new CustomOAuth2SuccessHandler(jwtTokenProvider);
    }

    @Test
    void onAuthenticationSuccess_ShouldReturnTokensInResponse() throws Exception {
        // Arrange
        String email = "test@example.com";
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        Map<String, Object> attributes = Map.of("email", email);
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
                Collections.emptyList(), attributes, "email"
        );

        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(jwtTokenProvider.createAccessToken(eq(email), eq("USER"))).thenReturn(accessToken);
        when(jwtTokenProvider.createRefreshToken(eq(email))).thenReturn(refreshToken);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        when(response.getOutputStream()).thenReturn(new jakarta.servlet.ServletOutputStream() {
            @Override
            public void write(int b) {
                outputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(jakarta.servlet.WriteListener writeListener) {

            }
        });


        successHandler.onAuthenticationSuccess(request, response, authentication);

        String responseContent = outputStream.toString();
        assertTrue(responseContent.contains(accessToken));
        assertTrue(responseContent.contains(refreshToken));
    }
}