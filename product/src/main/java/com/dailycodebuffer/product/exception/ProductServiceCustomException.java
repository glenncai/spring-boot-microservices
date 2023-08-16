package com.dailycodebuffer.product.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Product service custom exception
 *
 * @author Glenn Cai
 * @version 1.0 15/08/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductServiceCustomException extends RuntimeException {

  private final String errorCode;

  /**
   * Product service custom exception
   *
   * @param message   error message
   * @param errorCode error code
   */
  public ProductServiceCustomException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}
