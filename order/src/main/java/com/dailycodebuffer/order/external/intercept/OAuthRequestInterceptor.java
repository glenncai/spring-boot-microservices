package com.dailycodebuffer.order.external.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import javax.annotation.Resource;

/**
 * OAuth request interceptor
 *
 * @author Glenn Cai
 * @version 1.0 21/08/2023
 */
@Configuration
public class OAuthRequestInterceptor implements RequestInterceptor {

  @Resource
  private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", "Bearer "
        + oAuth2AuthorizedClientManager
        .authorize(OAuth2AuthorizeRequest
                       .withClientRegistrationId("internal-client")
                       .principal("internal")
                       .build())
        .getAccessToken().getTokenValue());
  }
}
