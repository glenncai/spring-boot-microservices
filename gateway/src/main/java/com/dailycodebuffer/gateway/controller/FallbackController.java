package com.dailycodebuffer.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fallback controller for the services
 *
 * @author Glenn Cai
 * @version 1.0 21/08/2023
 */
@RestController
public class FallbackController {

  @GetMapping("/orderServiceFallBack")
  public String orderServiceFallback() {
    return "Order service is down. Please try again later";
  }

  @GetMapping("/paymentServiceFallBack")
  public String paymentServiceFallback() {
    return "Payment service is down. Please try again later";
  }

  @GetMapping("/productServiceFallBack")
  public String productServiceFallback() {
    return "Product service is down. Please try again later";
  }
}
