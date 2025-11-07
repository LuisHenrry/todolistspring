package br.com.rocketseatproject.todolist.erros;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handlerHttpNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(e.getMostSpecificCause().getMessage());
    }
}
