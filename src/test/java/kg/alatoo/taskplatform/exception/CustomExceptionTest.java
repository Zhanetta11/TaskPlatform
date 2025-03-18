package kg.alatoo.taskplatform.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {

    @Test
    void testCustomException() {
        String expectedMessage = "Something went wrong";
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        CustomException exception = new CustomException(expectedMessage, expectedHttpStatus);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedHttpStatus, exception.getHttpStatus());
    }

    @Test
    void testToString() {
        String expectedMessage = "Something went wrong";
        HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        CustomException exception = new CustomException(expectedMessage, expectedHttpStatus);
        String exceptionString = exception.toString();

        assertTrue(exceptionString.contains(expectedMessage));
        assertTrue(exceptionString.contains(expectedHttpStatus.toString()));
    }
}