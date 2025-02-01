package ir.shop.application.exception;

import ir.shop.application.service.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<?> handleVerificationCodeException(InvalidVerificationCodeException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(CodeRequestTooSoonException.class)
    public ResponseEntity<?> handleCodeRequestTooSoonException(CodeRequestTooSoonException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(EndLoopException.class)
    public ResponseEntity<?> handleEndLoopException(EndLoopException ex) {
        return ResponseEntity.ok().body(new MessageResponse(ex.getMessage()));
    }
}
