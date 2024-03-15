package br.com.fiap.hackaton.customer.exceptions;

import br.com.fiap.hackaton.customer.records.ExceptionRecord;
import br.com.fiap.hackaton.customer.records.ExceptionValidationRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ExceptionRecord> personNotFoundException(ClienteNaoEncontradoException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionRecord(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }

    @ExceptionHandler(PassaparteObrigatorioException.class)
    public ResponseEntity<ExceptionRecord> passaparteObrigatorioException(PassaparteObrigatorioException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionRecord(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionValidationRecord> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionValidationRecord(HttpStatus.BAD_REQUEST, errors)
        );
    }
}