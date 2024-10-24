package com.pml.sistema_voto.entity.exceptions;

import java.io.Serial;

public class InvalidDocumentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    public InvalidDocumentException(String message) {
        super(message);
    }


    public InvalidDocumentException() {
        super("Documento inválido.");
    }
}

