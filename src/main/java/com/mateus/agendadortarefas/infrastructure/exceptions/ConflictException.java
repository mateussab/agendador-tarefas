package com.mateus.agendadortarefas.infrastructure.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String mensagem){
        super(mensagem);
    }
    public ConflictException(String mensagem, Throwable cause){
        super(mensagem,cause);

    }
}
