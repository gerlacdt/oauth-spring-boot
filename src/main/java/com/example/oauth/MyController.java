package com.example.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/login") // Optional: Redirect to OAuth provider
    public String login() {
        return "redirect:/login/oauth2/authorization/keycloak"; // Or github, keycloak, etc.
    }

    @GetMapping("/userinfo") // Access user info after login
    public String userInfo(Authentication authentication, Model model) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauthToken.getPrincipal();

        // Access user attributes:
        String name = user.getAttribute("name"); // Example for Google

        model.addAttribute("name", name);
        // ... process user info
        return "userinfo"; // Or redirect, etc.
    }
}