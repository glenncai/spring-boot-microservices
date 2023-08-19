package com.dailycodebuffer.order.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom exception
 *
 * @author Glenn Cai
 * @version 1.0 18/08/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {

  /**
   * Error code
   */
  private final String errorCode;

  /**
   * Error status
   */
  private final int status;

  public CustomException(String message, String errorCode, int status) {
    super(message);
    this.errorCode = errorCode;
    this.status = status;
  }
}
