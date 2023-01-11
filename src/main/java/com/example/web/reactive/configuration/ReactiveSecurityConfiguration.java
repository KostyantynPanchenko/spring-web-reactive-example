package com.example.web.reactive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ReactiveSecurityConfiguration {

  @Bean
  public ReactiveUserDetailsService reactiveUserDetailsService() {
    UserDetails user = User.withUsername("Frodo")
        .password(bCryptPasswordEncoder().encode("Baggins"))
        .authorities("read").build();
    return new MapReactiveUserDetailsService(user);
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
    return httpSecurity
        .httpBasic()
        .and()
          .authorizeExchange()
            .pathMatchers("/person")
              .authenticated()
            .pathMatchers("/person/**")
              .authenticated()
            .pathMatchers("/hello/**")
              .authenticated()
            .anyExchange()
              .permitAll()
        .and()
        .cors()
          .disable()
        .build();
  }
}
