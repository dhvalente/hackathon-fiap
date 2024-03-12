package br.com.fiap.hackaton.customer.records;

import br.com.fiap.hackaton.customer.entity.Endereco;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClienteRecord(
        @NotEmpty(message = "{name.required}")String name,
        @NotEmpty(message = "{lastName.required}")String lastName,
        @NotNull(message = "{birthDate.required}") LocalDate birthDate,
        @NotEmpty(message = "{phoneNumber.required}")String phoneNumber,
        @NotNull(message = "{address.required}") Endereco endereco
) {
}