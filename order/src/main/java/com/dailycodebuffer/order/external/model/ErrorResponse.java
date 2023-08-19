package com.dailycodebuffer.order.external.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom error response
 *
 * @author Glenn Cai
 * @version 1.0 15/8/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

  /**
   * Error message
   */
  private String errorMessage;

  /**
   * Error code
   */
  private String errorCode;
}

