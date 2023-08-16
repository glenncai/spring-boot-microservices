package com.dailycodebuffer.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order request body
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

  /**
   * Product id
   */
  private long productId;

  /**
   * Order quantity
   */
  private long quantity;

  /**
   * Total amount
   */
  private long totalAmount;

  /**
   * Payment mode
   */
  private PaymentMode paymentMode;
}
