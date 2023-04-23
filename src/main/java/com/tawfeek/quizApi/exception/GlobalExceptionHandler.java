package com.tawfeek.quizApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?>handleRecordNotFoundException(RecordNotFoundException ex, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(ex.getMessage(),webRequest.getDescription(false));
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorDetails);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?>handleQuantityException(ConflictException ex, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(ex.getMessage(),webRequest.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDetails);
    }




}
