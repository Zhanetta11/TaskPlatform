package kg.alatoo.taskplatform.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kg.alatoo.taskplatform.service.TwoFactorAuthService;
import kg.alatoo.taskplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/2fa")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class TwoFactorAuthController {

    private final TwoFactorAuthService twoFactorAuthService;
    private final UserService userService;

    @PostMapping("/enable")
    public String enable2FA(@RequestParam String email) {
        String secret = twoFactorAuthService.generateSecretKey();
        userService.enable2FA(email, secret);
        return "Secret key for Google Authenticator: " + secret;
    }

    @PostMapping("/verify")
    public String verify2FA(@RequestParam String email, @RequestParam int code) {
        boolean isValid = userService.verify2FACode(email, code);
        return isValid ? "2FA verified successfully" : "Invalid 2FA code!";
    }
}