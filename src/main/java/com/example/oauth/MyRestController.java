package com.example.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyRestController {

  @Autowired private OAuth2AuthorizedClientManager manager;

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/usertoken") // Access user info after login
  public String usertoken(Authentication authentication) {
    OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
    OAuth2AuthorizeRequest authRequest =
        OAuth2AuthorizeRequest.withClientRegistrationId(token.getAuthorizedClientRegistrationId())
            .principal(token)
            .build();
    OAuth2AuthorizedClient client = manager.authorize(authRequest);
    OAuth2AccessToken accessToken = client.getAccessToken();
    return accessToken.getTokenValue();
  }

  @GetMapping("/hello")
  public String hello(Authentication authentication) {
    JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
    String name = (String) oauthToken.getTokenAttributes().get("name");
    for (Map.Entry<?, ?> entry : oauthToken.getTokenAttributes().entrySet()) {
      logger.info("{} => {}", entry.getKey(), entry.getValue());
    }
    return String.format("hello %s", name);
  }

  @GetMapping("/greeting")
  public String greeting(Authentication authentication) {
    JwtAuthenticationToken oauthToken = (JwtAuthenticationToken) authentication;
    String name = (String) oauthToken.getTokenAttributes().get("name");
    for (Map.Entry<?, ?> entry : oauthToken.getTokenAttributes().entrySet()) {
      logger.info("{} => {}", entry.getKey(), entry.getValue());
    }
    return String.format("greetings %s", name);
  }
}
