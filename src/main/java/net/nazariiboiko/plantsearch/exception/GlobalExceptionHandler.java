package net.nazariiboiko.plantsearch.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(PlantNotFoundException.class)
    public ResponseEntity<String> handleException(PlantNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<String> handleException(PageNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PlantAlreadyExistsException.class)
    public ResponseEntity<String> handleException(PlantAlreadyExistsException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handleException(JwtAuthenticationException e) {
        return ResponseEntity.badRequest().build();
    }
}
