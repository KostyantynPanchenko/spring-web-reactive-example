package com.example.web.reactive.controller;

import static org.mockito.BDDMockito.given;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest
@Import(PersonController.class)
@ExtendWith(SpringExtension.class)
class PersonControllerTest {

  @Autowired
  private WebTestClient webTestClient;
  @MockBean
  private PersonService personService;
  private Person jordan;

  @BeforeEach
  void setUp() {
    jordan = new Person(23L, "Michael", "Jordan");
  }

  @Test
  void testGetOne() {
    given(personService.getById("23")).willReturn(Mono.just(jordan));
    webTestClient.get()
        .uri("/person/23")
        .accept(MediaType.APPLICATION_JSON).exchange()
        .expectStatus()
          .isOk()
        .expectHeader()
          .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
          .json("{\"id\":23, \"firstName\":\"Michael\", \"lastName\": \"Jordan\"}");

  }

  @Test
  void testGetAll() {
    Person pip = new Person(33L, "Scottie", "Pippen");
    given(personService.getAll()).willReturn(Flux.just(jordan, pip));
    Flux<Person> responseBody = webTestClient.get()
        .uri("/person")
        .accept(MediaType.APPLICATION_JSON).exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType("application/stream+json")
        .returnResult(Person.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectNext(new Person(23L, "Michael", "Jordan"))
        .expectNext(new Person(33L, "Scottie", "Pippen"))
        .verifyComplete();
  }
}
