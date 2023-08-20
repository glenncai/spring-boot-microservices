package com.dailycodebuffer.payment.controller;

import com.dailycodebuffer.payment.model.PaymentRequest;
import com.dailycodebuffer.payment.model.PaymentResponse;
import com.dailycodebuffer.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Payment controller class
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

  @Resource
  private PaymentService paymentService;

  /**
   * Do payment
   *
   * @return payment id
   */
  @PostMapping
  public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
    return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId) {
    return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId), HttpStatus.OK);
  }
}
