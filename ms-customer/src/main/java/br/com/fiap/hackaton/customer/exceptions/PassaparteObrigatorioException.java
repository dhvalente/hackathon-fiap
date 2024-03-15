package br.com.fiap.hackaton.customer.exceptions;

public class PassaparteObrigatorioException extends RuntimeException {

    public PassaparteObrigatorioException( ) {
        super("O cadastro do passaporte é obrigatório para estrangeiros");
    }
}