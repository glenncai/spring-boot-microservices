package com.dailycodebuffer.order.external.config;

import com.dailycodebuffer.order.external.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign configuration
 *
 * @author Glenn Cai
 * @version 1.0 18/08/2023
 */
@Configuration
public class FeignConfig {

  @Bean
  ErrorDecoder errorDecoder() {
    return new CustomErrorDecoder();
  }
}
