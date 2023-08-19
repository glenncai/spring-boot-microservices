package com.dailycodebuffer.order.exception;

import com.dailycodebuffer.order.external.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Rest response entity exception handler
 *
 * @author Glenn Cai
 * @version 1.0 18/08/2023
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle custom exception
   *
   * @param exception custom exception
   * @return error response entity
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
    return new ResponseEntity<>(
        ErrorResponse.builder()
                     .errorMessage(exception.getMessage())
                     .errorCode(exception.getErrorCode())
                     .build(),
        HttpStatus.valueOf(exception.getStatus()));
  }
}
