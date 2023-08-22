package com.dailycodebuffer.order.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.util.StreamUtils.copyToString;
import com.dailycodebuffer.order.OrderServiceConfig;
import com.dailycodebuffer.order.entity.Order;
import com.dailycodebuffer.order.model.OrderRequest;
import com.dailycodebuffer.order.model.OrderResponse;
import com.dailycodebuffer.order.model.PaymentMode;
import com.dailycodebuffer.order.repository.OrderRepository;
import com.dailycodebuffer.order.service.OrderService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.Resource;

/**
 * Order controller unit test
 *
 * @author Glenn Cai
 * @version 1.0 22/08/2023
 */
@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfig.class})
class OrderControllerTest {

  // Because we already set the port is 8088 in TestServiceInstanceListSupplier.java file
  @RegisterExtension
  static WireMockExtension wireMockServer =
      WireMockExtension.newInstance()
                       .options(WireMockConfiguration.wireMockConfig().port(8088))
                       .build();
  private final ObjectMapper objectMapper
      = new ObjectMapper()
      .findAndRegisterModules()
      .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  @Resource
  private OrderService orderService;
  @Resource
  private OrderRepository orderRepository;
  @Resource
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() throws IOException {
    getProductDetailsResponse();
    doPayment();
    getPaymentDetails();
    reduceQuantity();
  }

  private void reduceQuantity() {
    wireMockServer.stubFor(put(urlMatching("/api/product/reduceQuantity/.*"))
                               .willReturn(aResponse()
                                               .withStatus(HttpStatus.OK.value())
                                               .withHeader("Content-Type",
                                                           MediaType.APPLICATION_JSON_VALUE)));
  }

  private void getPaymentDetails() throws IOException {
    wireMockServer.stubFor(get(urlMatching("/api/payment/.*"))
                               .willReturn(aResponse()
                                               .withStatus(HttpStatus.OK.value())
                                               .withHeader("Content-Type",
                                                           MediaType.APPLICATION_JSON_VALUE)
                                               .withBody(
                                                   copyToString(
                                                       OrderControllerTest.class
                                                           .getClassLoader()
                                                           .getResourceAsStream(
                                                               "mock/GetPayment.json"),
                                                       defaultCharset()
                                                   )
                                               )));
  }

  private void doPayment() {
    wireMockServer.stubFor(post(urlEqualTo("/api/payment"))
                               .willReturn(aResponse()
                                               .withStatus(HttpStatus.OK.value())
                                               .withHeader("Content-Type",
                                                           MediaType.APPLICATION_JSON_VALUE)));
  }

  private void getProductDetailsResponse() throws IOException {
    wireMockServer.stubFor(get("/product/1")
                               .willReturn(aResponse()
                                               .withStatus(HttpStatus.OK.value())
                                               .withHeader("Content-Type",
                                                           MediaType.APPLICATION_JSON_VALUE)
                                               .withBody(copyToString(
                                                   OrderControllerTest.class
                                                       .getClassLoader()
                                                       .getResourceAsStream("mock/GetProduct.json"),
                                                   defaultCharset()
                                               ))));
  }

  private OrderRequest getMockOrderRequest() {
    return OrderRequest.builder()
                       .productId(1)
                       .paymentMode(PaymentMode.CASH)
                       .quantity(10)
                       .totalAmount(200)
                       .build();
  }

  @Test
  void test_WhenPlaceOrder_DoPayment_Success() throws Exception {
    // First Place Order
    // Get Order by Order Id from Db and check
    // Check Output

    OrderRequest orderRequest = getMockOrderRequest();
    MvcResult mvcResult
        = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                                                .with(jwt().authorities(
                                                    new SimpleGrantedAuthority("Customer")))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(
                                                    objectMapper.writeValueAsString(orderRequest))
                 ).andExpect(MockMvcResultMatchers.status().isOk())
                 .andReturn();

    String orderId = mvcResult.getResponse().getContentAsString();

    Optional<Order> order = orderRepository.findById(Long.valueOf(orderId));
    assertTrue(order.isPresent());

    Order o = order.get();
    assertEquals(Long.parseLong(orderId), o.getId());
    assertEquals("PLACED", o.getOrderStatus());
    assertEquals(orderRequest.getTotalAmount(), o.getAmount());
    assertEquals(orderRequest.getQuantity(), o.getQuantity());
  }

  @Test
  void test_WhenPlaceOrderWithWrongAccess_thenThrow403() throws Exception {
    OrderRequest orderRequest = getMockOrderRequest();
    MvcResult mvcResult
        = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                                                .with(jwt().authorities(
                                                    new SimpleGrantedAuthority("Admin")))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .content(
                                                    objectMapper.writeValueAsString(orderRequest))
                 ).andExpect(MockMvcResultMatchers.status().isForbidden())
                 .andReturn();
  }

  @Test
  void testWhen_GetOrder_Order_Not_Found() throws Exception {
    MvcResult mvcResult
        = mockMvc.perform(MockMvcRequestBuilders.get("/order/2")
                                                .with(jwt().authorities(
                                                    new SimpleGrantedAuthority("Admin")))
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(MockMvcResultMatchers.status().isNotFound())
                 .andReturn();
  }

  private String getOrderResponse(Order order) throws IOException {
    OrderResponse.PaymentDetails paymentDetails
        = objectMapper.readValue(
        copyToString(
            OrderControllerTest.class.getClassLoader()
                                     .getResourceAsStream("mock/GetPayment.json"
                                     ),
            defaultCharset()
        ), OrderResponse.PaymentDetails.class
    );
    paymentDetails.setPaymentStatus("SUCCESS");

    OrderResponse.ProductDetails productDetails
        = objectMapper.readValue(
        copyToString(
            OrderControllerTest.class.getClassLoader()
                                     .getResourceAsStream("mock/GetProduct.json"),
            defaultCharset()
        ), OrderResponse.ProductDetails.class
    );

    OrderResponse orderResponse
        = OrderResponse.builder()
                       .paymentDetails(paymentDetails)
                       .productDetails(productDetails)
                       .orderStatus(order.getOrderStatus())
                       .orderDate(order.getOrderDate())
                       .amount(order.getAmount())
                       .orderId(order.getId())
                       .build();
    return objectMapper.writeValueAsString(orderResponse);
  }
}