package com.dailycodebuffer.payment.service.impl;

import com.dailycodebuffer.payment.entity.TransactionDetails;
import com.dailycodebuffer.payment.model.PaymentMode;
import com.dailycodebuffer.payment.model.PaymentRequest;
import com.dailycodebuffer.payment.model.PaymentResponse;
import com.dailycodebuffer.payment.repository.TransactionDetailsRepository;
import com.dailycodebuffer.payment.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import javax.annotation.Resource;

/**
 * Payment service implementation
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

  @Resource
  private TransactionDetailsRepository transactionDetailsRepository;

  /**
   * Do payment
   *
   * @param paymentRequest payment request body
   * @return payment id
   */
  @Override
  public long doPayment(PaymentRequest paymentRequest) {
    log.info("Received payment request: {}", paymentRequest);

    TransactionDetails transactionDetails = TransactionDetails.builder()
                                                              .orderId(paymentRequest.getOrderId())
                                                              .amount(paymentRequest.getAmount())
                                                              .referenceNumber(
                                                                  paymentRequest.getReferenceNumber())
                                                              .paymentMode(
                                                                  paymentRequest.getPaymentMode()
                                                                                .name())
                                                              .paymentStatus("SUCCESS")
                                                              .paymentDate(Instant.now())
                                                              .build();
    transactionDetailsRepository.save(transactionDetails);
    log.info("Transaction completed with transaction id: {}", transactionDetails.getId());

    return transactionDetails.getId();
  }

  /**
   * Get payment details by order id
   *
   * @param orderId order id
   * @return payment response body
   */
  @Override
  public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
    log.info("Getting payment details for order id: {}", orderId);

    TransactionDetails transactionDetails =
        transactionDetailsRepository.findByOrderId(orderId);

    return PaymentResponse
        .builder()
        .paymentId(transactionDetails.getId())
        .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
        .paymentDate(transactionDetails.getPaymentDate())
        .orderId(transactionDetails.getOrderId())
        .paymentStatus(transactionDetails.getPaymentStatus())
        .amount(transactionDetails.getAmount())
        .build();
  }
}
