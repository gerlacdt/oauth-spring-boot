package com.example.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

  @Autowired private OAuth2AuthorizedClientManager manager;

  @GetMapping("/usertoken") // Access user info after login
  public String usertoken(Authentication authentication, Model model) {
    OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
    OAuth2AuthorizeRequest authRequest =
        OAuth2AuthorizeRequest.withClientRegistrationId(token.getAuthorizedClientRegistrationId())
            .principal(token)
            .build();
    OAuth2AuthorizedClient client = manager.authorize(authRequest);
    OAuth2AccessToken accessToken = client.getAccessToken();
    return accessToken.getTokenValue();
  }
}
