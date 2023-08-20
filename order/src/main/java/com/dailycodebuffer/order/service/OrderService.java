package com.dailycodebuffer.order.service;

import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.model.OrderResponse;

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

  /**
   * Get order details
   *
   * @param orderId order id
   * @return order response body
   */
  OrderResponse getOrderDetails(long orderId);
}
