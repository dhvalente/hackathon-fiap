package br.com.fiap.hackaton.customer.records;

import org.springframework.http.HttpStatus;

public record ExceptionRecord(HttpStatus statusCodeError, String messageError) {
}
