package com.example.oauth;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class MyRestControllerTest {

  @Autowired private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void testHello_authenticated_ok() throws Exception {
    this.mvc
        .perform(get("/hello").with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_profile"))))
        .andExpect(status().isOk());
  }

  @Test
  public void testHello_wrongScope_ok() throws Exception {
    this.mvc.perform(get("/hello").with(jwt())).andExpect(status().isForbidden());
  }

  @Test
  public void testHello_notAuthenticated_unauthorized() throws Exception {
    this.mvc.perform(get("/hello")).andExpect(status().isUnauthorized());
  }
}
