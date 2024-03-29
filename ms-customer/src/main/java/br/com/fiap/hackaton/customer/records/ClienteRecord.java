package br.com.fiap.hackaton.customer.records;

import br.com.fiap.hackaton.customer.entity.Endereco;
import br.com.fiap.hackaton.customer.enums.Generos;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClienteRecord(
        @NotEmpty(message = "{nome.required}") String nome,
        @NotEmpty(message = "{sobrenome.required}") String sobrenome,
        @NotEmpty(message = "{cpf.required}") String cpf ,
        @NotEmpty(message = "{passaporte.required}") String passaporte ,
        @NotNull(message = "{dataDeAniversario.required}") LocalDate dataDeAniversario,
        @NotNull(message = "{genero.required}") Generos genero ,
        @NotNull(message = "{endereco.required}") Endereco endereco,
        @NotEmpty(message = "{paisDeOrigem.required}")String paisDeOrigem,
        @NotEmpty(message = "{email.required}") String email,
        @NotEmpty(message = "{telefone.required}")String telefone

) {
}