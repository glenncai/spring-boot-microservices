package com.dailycodebuffer.order.external.client;

import com.dailycodebuffer.order.exception.CustomException;
import com.dailycodebuffer.order.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * External client for payment service. For better practice, we should create a new project named
 * "common" to implement all the external clients. But for simplicity, we just put it here.
 *
 * @author Glenn Cai
 * @version 1.0 20/08/2023
 */
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/api/payment")
public interface PaymentService {

  @PostMapping
  ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

  default ResponseEntity<Long> fallback(Exception e) {
    throw new CustomException("Payment service is not available", "UNAVAILABLE", 500);
  }
}
