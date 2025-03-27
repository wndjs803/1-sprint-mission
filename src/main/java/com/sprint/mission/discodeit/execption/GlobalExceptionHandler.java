package com.sprint.mission.discodeit.execption;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(DiscodeitException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode().getCode(),
        exception.getMessage());
    return ResponseEntity.status(HttpStatusCode.valueOf(exception.getErrorCode().getStatus()))
        .body(errorResponse);
  }
}
