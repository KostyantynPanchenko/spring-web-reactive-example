package com.example.web.reactive.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class FunctionalBasedVisitorController {

  @Bean
  public RouterFunction<ServerResponse> functionalHelloVisitor() {
    return RouterFunctions.route(getRequestPredicate(), getHandlerFunction());
  }

  private static RequestPredicate getRequestPredicate() {
    return RequestPredicates.GET("/functional/hello/visitor");
  }

  private static HandlerFunction<ServerResponse> getHandlerFunction() {
    return (ServerRequest request) -> ServerResponse.ok()
        .body(
            Mono.just("Hello functional " + request.queryParam("name").orElseThrow() + "!"),
            String.class);
  }
}
