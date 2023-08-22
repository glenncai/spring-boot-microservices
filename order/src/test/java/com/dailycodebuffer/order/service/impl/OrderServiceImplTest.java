package com.dailycodebuffer.order.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.dailycodebuffer.order.entity.Order;
import com.dailycodebuffer.order.exception.CustomException;
import com.dailycodebuffer.order.external.client.PaymentService;
import com.dailycodebuffer.order.external.client.ProductService;
import com.dailycodebuffer.order.external.request.PaymentRequest;
import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.model.OrderResponse;
import com.dailycodebuffer.order.model.PaymentMode;
import com.dailycodebuffer.order.model.PaymentResponse;
import com.dailycodebuffer.order.model.ProductResponse;
import com.dailycodebuffer.order.repository.OrderRepository;
import com.dailycodebuffer.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

/**
 * Order service unit test
 *
 * @author Glenn Cai
 * @version 1.0 22/08/2023
 */
@SpringBootTest
class OrderServiceImplTest {

  @InjectMocks
  OrderService orderService = new OrderServiceImpl();

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductService productService;
  @Mock
  private PaymentService paymentService;
  @Mock
  private RestTemplate restTemplate;

  @DisplayName("Get Order - Success Scenario")
  @Test
  void test_When_Order_Success() {
    // Mocking
    Order order = getMockOrder();
    when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
    when(restTemplate.getForObject("http://PRODUCT-SERVICE/api/product/" + order.getProductId(),
                                   ProductResponse.class)).thenReturn(getMockProductResponse());
    when(restTemplate.getForObject("http://PAYMENT-SERVICE/api/payment/order/" + order.getId(),
                                   PaymentResponse.class)).thenReturn(getMockPaymentResponse());

    // Actual
    OrderResponse orderResponse = orderService.getOrderDetails(order.getId());

    // Verification
    verify(orderRepository, times(1)).findById(anyLong());
    verify(restTemplate, times(1)).getForObject(
        "http://PRODUCT-SERVICE/api/product/" + order.getProductId(),
        ProductResponse.class);
    verify(restTemplate, times(1)).getForObject(
        "http://PAYMENT-SERVICE/api/payment/order/" + order.getId(),
        PaymentResponse.class);

    // Assertion
    assertNotNull(orderResponse);
    assertEquals(order.getId(), orderResponse.getOrderId());
  }

  @DisplayName("Get Order - Failure Scenario")
  @Test
  void test_When_Get_Order_NOT_FOUND_then_Not_Found() {
    // Mocking
    when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

    CustomException customException = assertThrows(CustomException.class,
                                                   () -> orderService.getOrderDetails(1L));
    assertEquals("NOT_FOUND", customException.getErrorCode());
    assertEquals(404, customException.getStatus());

    // Verification
    verify(orderRepository, times(1)).findById(anyLong());
  }

  private PaymentResponse getMockPaymentResponse() {
    return PaymentResponse.builder()
                          .paymentId(1)
                          .paymentDate(Instant.now())
                          .paymentMode(PaymentMode.CASH)
                          .amount(200)
                          .orderId(1)
                          .paymentStatus("ACCEPTED")
                          .build();
  }

  @DisplayName("Place Order - Success Scenario")
  @Test
  void test_When_Place_Order_Success() {
    // Mocking
    Order order = getMockOrder();
    OrderRequest orderRequest = getMockOrderRequest();

    when(productService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<>(
        HttpStatus.OK));
    when(orderRepository.save(any(Order.class))).thenReturn(order);
    when(paymentService.doPayment(any(PaymentRequest.class))).thenReturn(
        new ResponseEntity<>(1L, HttpStatus.OK));

    long orderId = orderService.placeOrder(orderRequest);

    // Verification
    verify(orderRepository, times(2)).save(any());
    verify(productService, times(1)).reduceQuantity(anyLong(), anyLong());
    verify(paymentService, times(1)).doPayment(any(PaymentRequest.class));

    // Assertion
    assertEquals(order.getId(), orderId);
  }

  @DisplayName("Place Order - Failure Scenario")
  @Test
  void test_when_Place_Order_Payment_Fails_then_Order_Placed() {
    // Mocking
    Order order = getMockOrder();
    OrderRequest orderRequest = getMockOrderRequest();

    when(productService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<>(
        HttpStatus.OK));
    when(orderRepository.save(any(Order.class))).thenReturn(order);
    when(paymentService.doPayment(any(PaymentRequest.class))).thenThrow(new RuntimeException());

    long orderId = orderService.placeOrder(orderRequest);

    // Verification
    verify(orderRepository, times(2)).save(any());
    verify(productService, times(1)).reduceQuantity(anyLong(), anyLong());
    verify(paymentService, times(1)).doPayment(any(PaymentRequest.class));

    // Assertion
    assertEquals(order.getId(), orderId);
  }

  private OrderRequest getMockOrderRequest() {
    return OrderRequest.builder()
                       .productId(1)
                       .quantity(10)
                       .paymentMode(PaymentMode.CASH)
                       .totalAmount(100)
                       .build();
  }

  private ProductResponse getMockProductResponse() {
    return ProductResponse.builder()
                          .productId(2)
                          .productName("iPhone")
                          .price(100)
                          .quantity(200)
                          .build();
  }

  private Order getMockOrder() {
    return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(200)
                .productId(2)
                .build();
  }
}