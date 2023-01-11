package com.example.web.reactive.controller;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.service.PersonService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AnnotationBasedVisitorController {

  private final PersonService personService;

  @GetMapping(path = "hello/visitor")
  public Mono<String> helloVisitor(@RequestParam(name = "name") final String name) {
    return Mono.just("Hello annotation based " + name + "!");
  }

  @GetMapping(path = "hello/all", produces = "application/stream+json")
  public Flux<Person> helloAll() {
    return personService.getAll().delayElements(Duration.ofMillis(300));
  }
}
