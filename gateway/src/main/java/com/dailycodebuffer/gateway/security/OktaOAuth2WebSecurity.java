package com.dailycodebuffer.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Okta OAuth2 Web Security configuration
 *
 * @author Glenn Cai
 * @version 1.0 21/08/2023
 */
@Configuration
@EnableWebFluxSecurity
public class OktaOAuth2WebSecurity {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
    serverHttpSecurity
        .authorizeExchange()
        .anyExchange()
        .authenticated()
        .and()
        .oauth2Login()
        .and()
        .oauth2ResourceServer()
        .jwt();
    return serverHttpSecurity.build();
  }
}
