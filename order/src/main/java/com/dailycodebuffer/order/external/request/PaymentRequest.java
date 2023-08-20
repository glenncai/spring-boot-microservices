package com.dailycodebuffer.order.external.request;

import com.dailycodebuffer.order.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment request body
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

  /**
   * Order id
   */
  private long orderId;

  /**
   * Amount
   */
  private long amount;

  /**
   * Reference number
   */
  private String referenceNumber;

  /**
   * Payment mode
   */
  private PaymentMode paymentMode;
}
