package com.example.FinalEvaluationTask.exceptionhandler;

import com.example.FinalEvaluationTask.exceptions.FetchDataException;
import com.example.FinalEvaluationTask.exceptions.LabelNotFoundException;
import com.example.FinalEvaluationTask.exceptions.NoApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(LabelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleLabelNotFoundException(LabelNotFoundException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoApiResponseException.class)
    public ResponseEntity<CustomErrorResponse> handleNoApiResponseException(NoApiResponseException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FetchDataException.class)
    public ResponseEntity<CustomErrorResponse> handleFetchDataException(FetchDataException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

