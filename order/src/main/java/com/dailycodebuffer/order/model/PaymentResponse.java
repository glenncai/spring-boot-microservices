package com.dailycodebuffer.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Payment response body
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

  /**
   * Payment id
   */
  private long paymentId;

  /**
   * Payment status
   */
  private String paymentStatus;

  /**
   * Payment mode
   */
  private PaymentMode paymentMode;

  /**
   * Amount
   */
  private long amount;

  /**
   * Payment date
   */
  private Instant paymentDate;

  /**
   * Order id
   */
  private long orderId;
}
