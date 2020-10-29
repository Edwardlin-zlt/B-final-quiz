package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TraineeNotExistException.class)
    public ResponseEntity<ErrorResult> handle(TraineeNotExistException traineeNotExistException) {
        String message = traineeNotExistException.getMessage();
        ErrorResult errorResult = new ErrorResult(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler(TrainerNotExistException.class)
    public ResponseEntity<ErrorResult> handle(TrainerNotExistException trainerNotExistException) {
        String message = trainerNotExistException.getMessage();
        ErrorResult errorResult = new ErrorResult(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }
}
