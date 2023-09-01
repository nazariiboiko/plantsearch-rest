package net.example.plantsearchrest.exception;

import lombok.RequiredArgsConstructor;
import net.example.plantsearchrest.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Messages messages;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Response> handleException(ServiceException e) {
        Response response = new Response();
        response.setMessage(messages.getMessage(e.getMessageCode()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleException(NotFoundException e) {
        Response response = new Response();
        response.setMessage(messages.getMessage(e.getMessageCode()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleException(AccessDeniedException e) {
        Response response = new Response();
        response.setMessage("Access Denied.");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
 }
