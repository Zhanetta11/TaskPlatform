package kg.alatoo.taskplatform.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        String secret = "mySecretKeyThatIsAtLeast32CharsLongForHMAC";
        ReflectionTestUtils.setField(jwtTokenProvider, "secret", secret);
        jwtTokenProvider.init();
    }

    @Test
    void createAccessToken() {
        String token = jwtTokenProvider.createAccessToken("user@example.com", "USER");
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("user@example.com", jwtTokenProvider.getEmail(token));
        assertEquals("USER", jwtTokenProvider.getRole(token));
    }

    @Test
    void createRefreshToken() {
        String token = jwtTokenProvider.createRefreshToken("user@example.com");
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("user@example.com", jwtTokenProvider.getEmail(token));
    }

    @Test
    void validateToken_ShouldReturnFalse_ForInvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("this.is.not.a.valid.token"));
    }

    @Test
    void getEmail() {
        String token = jwtTokenProvider.createAccessToken("user2@example.com", "ADMIN");
        String email = jwtTokenProvider.getEmail(token);
        assertEquals("user2@example.com", email);
    }

    @Test
    void getRole() {
        String token = jwtTokenProvider.createAccessToken("user3@example.com", "MODERATOR");
        String role = jwtTokenProvider.getRole(token);
        assertEquals("MODERATOR", role);
    }

    @Test
    void getAuthorities() {
        Collection<?> authorities = jwtTokenProvider.getAuthorities("admin");
        assertEquals(1, authorities.size());
        assertTrue(authorities.iterator().next().toString().contains("ROLE_ADMIN"));
    }
}
