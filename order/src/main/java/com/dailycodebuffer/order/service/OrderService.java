package com.dailycodebuffer.order.service;

import com.dailycodebuffer.order.model.OrderRequest;

/**
 * Order service
 *
 * @author Glenn Cai
 * @version 1.0 16/08/2023
 */
public interface OrderService {

  /**
   * Place order
   *
   * @param orderRequest order request body
   * @return order id
   */
  long placeOrder(OrderRequest orderRequest);
}
