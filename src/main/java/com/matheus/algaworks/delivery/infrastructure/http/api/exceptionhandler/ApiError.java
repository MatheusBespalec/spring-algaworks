package com.matheus.algaworks.delivery.infrastructure.http.api.exceptionhandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private final Boolean error = true;
    private final Integer status;
    private final ApiErrorType type;
    private final String title;
    private final String detail;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(ApiErrorType type, String title, String detail, HttpStatus status) {
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.status = status.value();
    }
}
