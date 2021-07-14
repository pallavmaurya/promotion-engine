package com.example.controller;

import com.example.exception.SkuPriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BadRequestControllerAdvice {

    public BadRequestControllerAdvice() {
        super();
    }

    @ExceptionHandler(SkuPriceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handle(SkuPriceNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handle(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(this.getErrorMessages(exception));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handle(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private List<String> getErrorMessages(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getRejectedValue()
                        + ", ErrorMessage: " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
