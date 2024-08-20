package com.soro.esop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
  prePostEnabled = true,  // Enables Spring Security pre/post annotations
  securedEnabled = true,  // Enables the @Secured annotation
  jsr250Enabled = true)   // Enables the @RolesAllowed annotation
public class MethodSecurityConfig {    
}
