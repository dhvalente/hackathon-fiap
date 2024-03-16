package br.com.fiap.hackaton.ms.reservas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservaExceptionHandler {

  @ExceptionHandler(GenericException.class)
  public ResponseEntity<Object> handleReservaException(GenericException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  // Classe interna para representar erros de API de maneira padronizada
  private static class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
      this.status = status;
      this.message = message;
    }

    // Getters
    public HttpStatus getStatus() {
      return status;
    }

    public String getMessage() {
      return message;
    }
  }
}
