package br.com.fiap.hackaton.customer.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Long id) {
        super(String.format("Cliente com ID: %s não encontrado(a)", id));
    }
}