package com.dailycodebuffer.order.service.impl;

import com.dailycodebuffer.order.entity.Order;
import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.repository.OrderRepository;
import com.dailycodebuffer.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import javax.annotation.Resource;

/**
 * Order service implementation
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

  @Resource
  private OrderRepository orderRepository;

  /**
   * Place order
   *
   * @param orderRequest order request body
   * @return order id
   */
  @Override
  public long placeOrder(OrderRequest orderRequest) {
    log.info("Placing order request: {}", orderRequest);
    Order order = Order.builder()
                       .amount(orderRequest.getTotalAmount())
                       .orderStatus("CREATED")
                       .productId(orderRequest.getProductId())
                       .quantity(orderRequest.getQuantity())
                       .orderDate(Instant.now())
                       .build();
    order = orderRepository.save(order);
    log.info("Order placed successfully with order id: {}", order.getId());
    return order.getId();
  }
}
