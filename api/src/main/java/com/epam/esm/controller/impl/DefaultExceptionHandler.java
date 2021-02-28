package com.epam.esm.controller.impl;

import com.epam.esm.jpa.exception.DaoException;
import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.jpa.exception.OrderNotFoundException;
import com.epam.esm.jpa.exception.TagNotFoundException;
import com.epam.esm.jpa.exception.UserNotFoundException;
import com.epam.esm.jpa.exception.TagNameRegisteredException;
import com.epam.esm.model.dto.ErrorResponse;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.util.Status;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class DefaultExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(value = {
            ServiceException.class,
            DaoException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest webRequest, HttpServletRequest request) {
        String message = messageSource.getMessage(Status.DEFAULT.getCode().toString(), null, webRequest.getLocale());

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.DEFAULT, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(GiftNotFoundException.class)
    @SneakyThrows
    public ResponseEntity<ErrorResponse> handleGiftNotFound(HttpServletRequest request, HttpServletResponse response, Exception ex, WebRequest webRequest) {
        String message = messageSource.getMessage(Status.GIFT_NOT_FOUND.getCode().toString(), null, webRequest.getLocale());

        String resultMessage = errorMessageNotFoundById(message, ex);

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.GIFT_NOT_FOUND, HttpStatus.NOT_FOUND, resultMessage);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @SneakyThrows
    public ResponseEntity<ErrorResponse> handleTagNotFound(HttpServletRequest request, HttpServletResponse response, Exception ex, WebRequest webRequest) {
        String message = messageSource.getMessage(Status.TAG_NOT_FOUND.getCode().toString(), null, webRequest.getLocale());

        String resultMessage = errorMessageNotFoundById(message, ex);

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.TAG_NOT_FOUND, HttpStatus.NOT_FOUND, resultMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @SneakyThrows
    public ResponseEntity<ErrorResponse> handleUserNotFound(HttpServletRequest request, HttpServletResponse response, Exception ex, WebRequest webRequest) {
        String message = messageSource.getMessage(Status.USER_NOT_FOUND.getCode().toString(), null, webRequest.getLocale());

        String resultMessage = errorMessageNotFoundById(message, ex);

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.USER_NOT_FOUND, HttpStatus.NOT_FOUND, resultMessage);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @SneakyThrows
    public ResponseEntity<ErrorResponse> handleOrderNotFound(HttpServletRequest request, HttpServletResponse response, Exception ex, WebRequest webRequest) {
        String message = messageSource.getMessage(Status.ORDER_NOT_FOUND.getCode().toString(), null, webRequest.getLocale());

        String resultMessage = errorMessageNotFoundById(message, ex);

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND, resultMessage);
    }

    @ExceptionHandler(TagNameRegisteredException.class)
    @SneakyThrows
    public ResponseEntity<ErrorResponse> handleTagNameRegistered(HttpServletRequest request, HttpServletResponse response, Exception ex, WebRequest webRequest) {
        String message = messageSource.getMessage(Status.TAG_NAME_ALREADY_REGISTERED.getCode().toString(), null, webRequest.getLocale());

        return getErrorResponseResponseEntity(request, ex, webRequest, Status.TAG_NAME_ALREADY_REGISTERED, HttpStatus.CONFLICT, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationErrorHandler(MethodArgumentNotValidException e) {
        List<String> errorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(Status.VALIDATION_EXCEPTION.getCode())
                .comment(errorList)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(HttpServletRequest request, Exception ex, WebRequest webRequest, Status status, HttpStatus httpStatus, String message) {
        log.error("AT URI: " + request.getRequestURI() + " HANDLE EXCEPTION: " + ex);

        ErrorResponse body = ErrorResponse.builder()
                .code(status.getCode())
                .comment(Collections.singletonList(message))
                .build();

        return ResponseEntity.status(httpStatus).body(body);
    }

    private String errorMessageNotFoundById(String message, Exception ex) {
        return message + " (id) = " + ex.getMessage();
    }
}
