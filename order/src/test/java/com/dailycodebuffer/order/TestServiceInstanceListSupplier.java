package com.dailycodebuffer.order;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Test service instance list supplier
 *
 * @author Glenn Cai
 * @version 1.0 22/08/2023
 */
public class TestServiceInstanceListSupplier implements ServiceInstanceListSupplier {
  @Override
  public String getServiceId() {
    return null;
  }

  @Override
  public Flux<List<ServiceInstance>> get() {
    List<ServiceInstance> result = new ArrayList<>();
    result.add(new DefaultServiceInstance(
        "PRODUCT-SERVICE",
        "PRODUCT-SERVICE",
        "localhost",
        8088,
        false
    ));

    result.add(new DefaultServiceInstance(
        "PAYMENT-SERVICE",
        "PAYMENT-SERVICE",
        "localhost",
        8088,
        false
    ));

    return Flux.just(result);
  }
}
