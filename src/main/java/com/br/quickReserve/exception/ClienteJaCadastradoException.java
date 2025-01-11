package com.br.quickReserve.exception;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException() {
        super("Cliente com CPF/E-mail já cadastrado!");
    }
}
