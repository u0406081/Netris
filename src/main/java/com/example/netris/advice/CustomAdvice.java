package com.example.netris.advice;

import com.example.netris.dto.AggregateResult;
import com.example.netris.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleException(CustomException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
    }

}
