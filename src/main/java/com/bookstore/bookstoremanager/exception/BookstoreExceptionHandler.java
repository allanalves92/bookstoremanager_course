package com.bookstore.bookstoremanager.exception;

import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@ControllerAdvice
public class BookstoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage(),
                Collections.singletonList(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add("Field " + fieldError.getField().toUpperCase() + " " + fieldError.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors().forEach(globalErrors -> errors.add("Object"
                + " " + globalErrors.getObjectName() + " " + globalErrors.getDefaultMessage()));

        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Informed argument(s) validation " +
                "error" + "(s)", errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Malformed JSON body and/or field " +
                "error", Collections.singletonList(exception.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message,
                                                       List<String> errors) {
        ApiError apiError =
                ApiError.builder().code(httpStatus.value()).status(httpStatus.getReasonPhrase()).message(message).errors(errors).timestamp(LocalDateTime.now()).build();
        return ResponseEntity.status(httpStatus).body(apiError);
    }

}
