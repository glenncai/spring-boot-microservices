package com.dailycodebuffer.gateway.controller;

import com.dailycodebuffer.gateway.model.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller.
 * <p>
 * This is not the best practice to implement user authentication in cloud gateway, I recommend to
 * implement authentication in individual microservices.
 *
 * @author Glenn Cai
 * @version 1.0 21/08/2023
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

  @GetMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @AuthenticationPrincipal OidcUser oidcUser,
      Model model,
      @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient
  ) {
    AuthenticationResponse authenticationResponse
        = AuthenticationResponse.builder()
                                .userId(oidcUser.getEmail())
                                .accessToken(authorizedClient.getAccessToken().getTokenValue())
                                .refreshToken(authorizedClient.getRefreshToken().getTokenValue())
                                .expiresAt(authorizedClient.getAccessToken().getExpiresAt()
                                                           .getEpochSecond())
                                .authorityList(oidcUser.getAuthorities().stream()
                                                       .map(GrantedAuthority::getAuthority)
                                                       .toList())
                                .build();
    return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
  }
}
