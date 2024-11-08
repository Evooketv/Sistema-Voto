package com.pml.sistema_voto.entity.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pml.sistema_voto.entity.VotoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<VotoResponseDTO> handleInvalidFormatException(InvalidFormatException e) {
        String mensagem = "Opção de voto inválida: " + e.getValue();

        return ResponseEntity.badRequest().body(new VotoResponseDTO(null, null, null, false, null, null, mensagem, null));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<VotoResponseDTO> handleJsonProcessingException(JsonProcessingException e) {
        String mensagem = "Erro ao processar JSON: " + e.getMessage();
        return ResponseEntity.badRequest().body(new VotoResponseDTO(null, null, null, false, null, null, mensagem, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

