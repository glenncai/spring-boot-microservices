package com.dailycodebuffer.order;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

/**
 * Order service config
 *
 * @author Glenn Cai
 * @version 1.0 22/08/2023
 */
@TestConfiguration
public class OrderServiceConfig {

  @Bean
  public ServiceInstanceListSupplier serviceInstanceListSupplier() {
    return new TestServiceInstanceListSupplier();
  }
}
