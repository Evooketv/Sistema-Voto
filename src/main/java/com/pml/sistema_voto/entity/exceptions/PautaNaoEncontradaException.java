package com.pml.sistema_voto.entity.exceptions;

public class PautaNaoEncontradaException extends RuntimeException {
    public PautaNaoEncontradaException(String message) {
        super(message);
    }
}
