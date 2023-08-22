package com.dailycodebuffer.order.external.client;

import com.dailycodebuffer.order.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * External client for product service. For better practice, we should create a new project named
 * "common" to implement all the external clients. But for simplicity, we just put it here.
 *
 * @author Glenn Cai
 * @version 1.0 17/08/2023
 */
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/api/product")
public interface ProductService {

  @PutMapping("/reduceQuantity/{id}")
  ResponseEntity<Void> reduceQuantity(@PathVariable("id") long id,
                                      @RequestParam long quantity);

  default ResponseEntity<Void> fallback(Exception e) {
    throw new CustomException("Product service is not available", "UNAVAILABLE", 500);
  }
}
