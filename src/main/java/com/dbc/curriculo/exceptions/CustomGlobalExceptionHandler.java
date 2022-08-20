package com.dbc.curriculo.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request){
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @NotNull
    private ResponseEntity<Object> returnError(String exception, HttpStatus httpStatus) {
        DefaultError defaultError = new DefaultError();
        defaultError.setTimestamp(new Date());
        defaultError.setStatus(httpStatus.value());
        defaultError.setErrors(List.of(exception));
        return new ResponseEntity<>(defaultError, httpStatus);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleException(DisabledException exception){
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException exception){
        return returnError(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleException(UsernameNotFoundException exception) {
        return returnError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleException(InvalidFormatException exception) {
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleException(DataIntegrityViolationException exception) {
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdentifierGenerationException.class)
    public ResponseEntity<Object> handleException(IdentifierGenerationException exception) {
        return returnError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleException(ApiException exception) {
        return returnError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CandidatoException.class)
    public ResponseEntity<Object> handleException(CandidatoException exception) {
        return returnError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CandidatoValidarException.class)
    public ResponseEntity<Object> handleException(CandidatoValidarException exception) {
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleException(LoginException exception) {
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleException(IOException exception) {
        String mensagem = "Error response: Service Unavailable.";
        return returnError(mensagem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<Object> handleException(DefaultException exception) {
        return returnError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleException(ExpiredJwtException exception) {
        String mensagem = "Token expirou.";
        return returnError(mensagem, HttpStatus.BAD_REQUEST);
    }

}
