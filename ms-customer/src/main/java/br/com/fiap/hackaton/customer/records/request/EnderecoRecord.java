package br.com.fiap.hackaton.customer.records.request;

import jakarta.validation.constraints.NotEmpty;

public record EnderecoRecord(
        @NotEmpty(message = "{street.required}")String street,
        @NotEmpty(message = "{number.required}")String number,
        @NotEmpty(message = "{district.required}")String district,
        @NotEmpty(message = "{state.required}")String state,
        @NotEmpty(message = "{city.required}")String city ) {
}
