package com.dailycodebuffer.product.exception;

import com.dailycodebuffer.product.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Rest response entity exception handler
 *
 * @author Glenn Cai
 * @version 1.0 15/08/2023
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle product service exception
   *
   * @param exception product service custom exception
   * @return error response entity
   */
  @ExceptionHandler(ProductServiceCustomException.class)
  public ResponseEntity<ErrorResponse> handleProductServiceException(
      ProductServiceCustomException exception) {
    return new ResponseEntity<>(ErrorResponse.builder()
                                             .errorMessage(exception.getMessage())
                                             .errorCode(exception.getErrorCode())
                                             .build(), HttpStatus.NOT_FOUND);
  }
}
