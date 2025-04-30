package com.renan.usuario.infrastructure.exceptions;

public class ConflictExceptions extends RuntimeException {
    public ConflictExceptions(String message) {
        super(message);
    }
    public ConflictExceptions(String mensagem, Throwable throwable){
      super(mensagem, throwable);
    }
}
