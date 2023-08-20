package com.dailycodebuffer.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Order response body
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

  /**
   * Order id
   */
  private long orderId;

  /**
   * Order date
   */
  private Instant orderDate;

  /**
   * Order status
   */
  private String orderStatus;

  /**
   * Order amount
   */
  private long amount;

  /**
   * Product details
   */
  private ProductDetails productDetails;

  /**
   * Payment details
   */
  private PaymentDetails paymentDetails;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductDetails {

    /**
     * Product id
     */
    private long productId;

    /**
     * Product name
     */
    private String productName;

    /**
     * Product price
     */
    private long price;

    /**
     * Product quantity
     */
    private long quantity;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class PaymentDetails {
    /**
     * Payment id
     */
    private long paymentId;

    /**
     * Payment mode
     */
    private PaymentMode paymentMode;

    /**
     * Payment status
     */
    private String paymentStatus;

    /**
     * Payment date
     */
    private Instant paymentDate;
  }
}
