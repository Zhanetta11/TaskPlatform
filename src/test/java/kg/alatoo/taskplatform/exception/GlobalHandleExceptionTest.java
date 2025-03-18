package kg.alatoo.taskplatform.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GlobalHandleExceptionTest {

    @Autowired
    private GlobalHandleException globalHandleException;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(globalHandleException).build();
    }

    @Test
    public void testHandleException() {
        String message = "Custom exception occurred";
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomException customException = new CustomException(message, status);

        ResponseEntity<Object> response = globalHandleException.handleException(customException);

        assertEquals(message, response.getBody());
        assertEquals(status, response.getStatusCode());
    }
}