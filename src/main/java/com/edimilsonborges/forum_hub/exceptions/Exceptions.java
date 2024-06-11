package com.edimilsonborges.forum_hub.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class Exceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarErro404(){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException exception){
        List<FieldError> erros =  exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> duplicata(SQLIntegrityConstraintViolationException exception){
        return ResponseEntity.internalServerError().body(new DadosErrosDuplicata(exception.getMessage()));
    }

    public record DadosErrosValidacao(String campo, String mensagem){
        public DadosErrosValidacao(FieldError fieldError){
            this(fieldError.getField(),fieldError.getDefaultMessage());
        }
    }

    public record DadosErrosDuplicata(String erro){

    }
}
