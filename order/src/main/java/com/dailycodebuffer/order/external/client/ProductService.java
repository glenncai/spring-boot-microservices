package com.dailycodebuffer.order.external.client;

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
@FeignClient(name = "PRODUCT-SERVICE/api/product")
public interface ProductService {

  @PutMapping("/reduceQuantity/{id}")
  ResponseEntity<Void> reduceQuantity(@PathVariable("id") long id,
                                      @RequestParam long quantity);
}
