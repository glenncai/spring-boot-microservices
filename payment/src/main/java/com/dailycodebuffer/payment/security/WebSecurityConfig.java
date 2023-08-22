package com.dailycodebuffer.payment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring security configuration
 *
 * @author Glenn Cai
 * @version 1.0 21/08/2023
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(
        authorizeRequests -> authorizeRequests.antMatchers("/api/payment/**")
                                              .hasAuthority("SCOPE_internal").anyRequest()
                                              .authenticated()).oauth2ResourceServer(
        OAuth2ResourceServerConfigurer::jwt);
    return httpSecurity.build();
  }
}
