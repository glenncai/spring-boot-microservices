package com.dailycodebuffer.order.service.impl;

import com.dailycodebuffer.order.entity.Order;
import com.dailycodebuffer.order.exception.CustomException;
import com.dailycodebuffer.order.external.client.PaymentService;
import com.dailycodebuffer.order.external.client.ProductService;
import com.dailycodebuffer.order.external.request.PaymentRequest;
import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.model.OrderResponse;
import com.dailycodebuffer.order.model.PaymentResponse;
import com.dailycodebuffer.order.model.ProductResponse;
import com.dailycodebuffer.order.repository.OrderRepository;
import com.dailycodebuffer.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

  @Resource
  private ProductService productService;

  @Resource
  private PaymentService paymentService;

  @Resource
  private RestTemplate restTemplate;

  /**
   * Place order
   *
   * @param orderRequest order request body
   * @return order id
   */
  @Override
  public long placeOrder(OrderRequest orderRequest) {
    log.info("Placing order request: {}", orderRequest);

    productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

    Order order = Order.builder()
                       .amount(orderRequest.getTotalAmount())
                       .orderStatus("CREATED")
                       .productId(orderRequest.getProductId())
                       .quantity(orderRequest.getQuantity())
                       .orderDate(Instant.now())
                       .build();
    order = orderRepository.save(order);

    log.info("Calling payment service");
    PaymentRequest paymentRequest = PaymentRequest.builder()
                                                  .orderId(order.getId())
                                                  .amount(order.getAmount())
                                                  .paymentMode(orderRequest.getPaymentMode())
                                                  .build();

    String orderStatus;
    try {
      paymentService.doPayment(paymentRequest);
      log.info("Payment successful");
      orderStatus = "PLACED";
    } catch (Exception e) {
      log.error("Error occurred while calling payment service: {}", e.getMessage());
      orderStatus = "PAYMENT_FAILED";
    }

    order.setOrderStatus(orderStatus);
    orderRepository.save(order);

    log.info("Order placed successfully with order id: {}", order.getId());
    return order.getId();
  }

  /**
   * Get order details
   *
   * @param orderId order id
   * @return order response body
   */
  @Override
  public OrderResponse getOrderDetails(long orderId) {
    log.info("Getting order details for order id: {}", orderId);

    Order order = orderRepository.findById(orderId).orElseThrow(
        () -> new CustomException("Order not found with id: " + orderId, "NOT_FOUND", 404));

    log.info("Invoking product service to fetch the product for product id: {}",
             order.getProductId());
    ProductResponse productResponse
        = restTemplate.getForObject(
        "http://PRODUCT-SERVICE/api/product/" + order.getProductId(),
        ProductResponse.class);

    log.info("Getting payment information from the payment service");
    PaymentResponse paymentResponse
        = restTemplate.getForObject(
        "http://PAYMENT-SERVICE/api/payment/order/" + order.getId(),
        PaymentResponse.class);

    OrderResponse.ProductDetails productDetails
        = OrderResponse.ProductDetails
        .builder()
        .productId(productResponse.getProductId())
        .productName(productResponse.getProductName())
        .build();

    OrderResponse.PaymentDetails paymentDetails
        = OrderResponse.PaymentDetails
        .builder()
        .paymentId(paymentResponse.getPaymentId())
        .paymentStatus(paymentResponse.getPaymentStatus())
        .paymentMode(paymentResponse.getPaymentMode())
        .paymentDate(paymentResponse.getPaymentDate())
        .build();

    return OrderResponse.builder()
                        .orderId(order.getId())
                        .orderDate(order.getOrderDate())
                        .orderStatus(order.getOrderStatus())
                        .amount(order.getAmount())
                        .productDetails(productDetails)
                        .paymentDetails(paymentDetails)
                        .build();
  }
}
