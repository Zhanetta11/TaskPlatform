package kg.alatoo.taskplatform.controllers;

import kg.alatoo.taskplatform.security.JwtAuthenticationFilter;
import kg.alatoo.taskplatform.security.JwtTokenProvider;
import kg.alatoo.taskplatform.service.TwoFactorAuthService;
import kg.alatoo.taskplatform.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TwoFactorAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class TwoFactorAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwoFactorAuthService twoFactorAuthService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void enable2FA_ShouldReturnSecretKey() throws Exception {
        String email = "user@example.com";
        String fakeSecret = "ABC123XYZ";

        when(twoFactorAuthService.generateSecretKey()).thenReturn(fakeSecret);

        mockMvc.perform(post("/2fa/enable")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Secret key for Google Authenticator: " + fakeSecret));

        verify(userService).enable2FA(email, fakeSecret);
    }

    @Test
    void verify2FA_ShouldReturnSuccessMessage_WhenCodeIsValid() throws Exception {
        String email = "user@example.com";
        int code = 123456;

        when(userService.verify2FACode(email, code)).thenReturn(true);

        mockMvc.perform(post("/2fa/verify")
                        .param("email", email)
                        .param("code", String.valueOf(code)))
                .andExpect(status().isOk())
                .andExpect(content().string("2FA verified successfully"));
    }

    @Test
    void verify2FA_ShouldReturnFailureMessage_WhenCodeIsInvalid() throws Exception {
        String email = "user@example.com";
        int code = 654321;

        when(userService.verify2FACode(email, code)).thenReturn(false);

        mockMvc.perform(post("/2fa/verify")
                        .param("email", email)
                        .param("code", String.valueOf(code)))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid 2FA code!"));
    }
}