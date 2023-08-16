package com.dailycodebuffer.order.controller;

import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Order controller
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

  @Resource
  private OrderService orderService;

  @PostMapping("/placeOrder")
  public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
    long orderId = orderService.placeOrder(orderRequest);
    return new ResponseEntity<>(orderId, HttpStatus.OK);
  }
}
