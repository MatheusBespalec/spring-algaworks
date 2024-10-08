package com.matheus.algaworks.delivery.infrastructure.http.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.matheus.algaworks.delivery.domain.exeption.EntityInUseException;
import com.matheus.algaworks.delivery.domain.exeption.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof PropertyBindingException) {
            return this.handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        } else if (rootCause instanceof MismatchedInputException) {
            return this.handleMismatchedInputException((MismatchedInputException) rootCause, headers, status, request);
        }
        ApiError apiError = new ApiError(ApiErrorType.JSON_ERROR, "Json mal formatado", "O JSON do corpo da mensagem contém erros de sintaxe. Verifique o corpo da mensagem enviada", HttpStatus.BAD_REQUEST);
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    public ResponseEntity<Object> handleMismatchedInputException(MismatchedInputException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining("."));
        String detail = String.format("O parametro '%s' deve ser do tipo '%s'", path, ex.getTargetType().getSimpleName());
        ApiError apiError = new ApiError(ApiErrorType.VALIDATION_ERROR, "Argumento(s) Inválidos", detail, HttpStatus.BAD_REQUEST);
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    public ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format("O argumento '%s' enviado não é esperado, remova para realizar a requisição", path);
        ApiError apiError = new ApiError(ApiErrorType.VALIDATION_ERROR, "Argumento(s) Inválidos", detail, HttpStatus.BAD_REQUEST);
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        ApiErrorType.RESOURCE_NOT_FOUND,
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage(), HttpStatus.NOT_FOUND
                ));
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(EntityInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        ApiErrorType.RESOURCE_IN_USE,
                        "Recurso em uso",
                        e.getMessage(),
                        HttpStatus.CONFLICT
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleThrowable(Exception ex, WebRequest request) {
        return this.handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String detail = String.format("O parametro da URL '%s' é inválido", ex.getParameter().getParameterName());
        ApiError apiError = new ApiError(ApiErrorType.VALIDATION_ERROR, "Erro de validação", detail, httpStatus);
        return this.handleExceptionInternal(ex, apiError, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode.value());
        if (body == null) {
            body = new ApiError(ApiErrorType.GENERIC, httpStatus.getReasonPhrase(), httpStatus.getReasonPhrase(), httpStatus);
        } else if (body instanceof String) {
            body = new ApiError(ApiErrorType.GENERIC, httpStatus.getReasonPhrase(), (String) body, httpStatus);
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return this.handleExceptionInternal(ex, null, headers, status, request);
    }
}
