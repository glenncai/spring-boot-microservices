package com.dailycodebuffer.payment.service;

import com.dailycodebuffer.payment.model.PaymentRequest;
import com.dailycodebuffer.payment.model.PaymentResponse;

/**
 * Payment service interface
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
public interface PaymentService {
  /**
   * Do payment
   *
   * @param paymentRequest payment request body
   * @return payment id
   */
  long doPayment(PaymentRequest paymentRequest);

  /**
   * Get payment details by order id
   *
   * @param orderId order id
   * @return payment response body
   */
  PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
