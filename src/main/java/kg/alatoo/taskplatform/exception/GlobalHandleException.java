package kg.alatoo.taskplatform.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalHandleException {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }
}