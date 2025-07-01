package com.organizaAi.OrganizaAi.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.organizaAi.OrganizaAi.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDTO response = new ErrorResponseDTO(401, errors);

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyExists(AlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());

        ErrorResponseDTO response = new ErrorResponseDTO(409, errors);

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());     

        ErrorResponseDTO response = new ErrorResponseDTO(404, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());       

        ErrorResponseDTO response = new ErrorResponseDTO(403, errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

}
