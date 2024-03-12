package br.com.fiap.hackaton.customer.records;

import org.springframework.http.HttpStatus;

public record ExceptionValidationRecord(HttpStatus statusCodeError, java.util.Map<String, String> messageError) {
}
