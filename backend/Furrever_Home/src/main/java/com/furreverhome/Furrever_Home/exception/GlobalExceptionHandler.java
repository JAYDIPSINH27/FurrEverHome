package com.furreverhome.Furrever_Home.exception;
import com.furreverhome.Furrever_Home.utils.ErrorDetails;
import io.getstream.chat.java.exceptions.StreamException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // General exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        HttpStatus httpStatus;
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDate(new Date());
        errorDetails.setMessage(ex.getMessage());

        if (ex instanceof MethodArgumentNotValidException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BadCredentialsException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    // Specific exception handler
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StreamException.class)
    public ResponseEntity<?> handleStreamException(StreamException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDate(new Date());
        // Customize the message as needed, or use ex.getMessage() directly
        errorDetails.setMessage("Error processing chat request.");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

