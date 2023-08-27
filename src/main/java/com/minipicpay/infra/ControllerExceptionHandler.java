package com.minipicpay.infra;

import com.minipicpay.dtos.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicateEntry() {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", 400);
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> threatNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> threatGeneralException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), 400);
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
