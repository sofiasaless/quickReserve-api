package com.br.quickReserve.exception;

public class RestauranteJaCadastradoException extends RuntimeException {
    public RestauranteJaCadastradoException() {
        super("Restaurante com CNPJ/Email já cadastrado!");
    }
}
