package com.example.web.reactive.controller;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.service.PersonService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;

  @Bean
  public RouterFunction<ServerResponse> personRoutes() {
    return RouterFunctions.route()
        .GET("person/{id}", getByIdHandler())
        .GET("person", getAllHandler())
        .PUT("person", updateOneHandler())
        .build();
  }

  private HandlerFunction<ServerResponse> getByIdHandler() {
    return (ServerRequest request) ->
      ServerResponse.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(personService.getById(request.pathVariable("id")), Person.class);
  }

  private HandlerFunction<ServerResponse> getAllHandler() {
    return request -> ServerResponse.ok()
        .header("Content-Type", "application/stream+json")
        .body(personService.getAll().delayElements(Duration.ofMillis(333)), Person.class);
  }

  private HandlerFunction<ServerResponse> updateOneHandler() {
    return request -> request.bodyToMono(Person.class)
        .flatMap(personService::updatePerson)
        .flatMap(person -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(person));
  }
}
