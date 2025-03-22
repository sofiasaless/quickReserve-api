package com.br.quickReserve.exception;

public class AlteracaoNaoAutorizadaException extends RuntimeException {
    public AlteracaoNaoAutorizadaException() {
        super("A reserva não pode ser alterada qua está CONFIRMADA ou CANCELADA!");
    }
}
