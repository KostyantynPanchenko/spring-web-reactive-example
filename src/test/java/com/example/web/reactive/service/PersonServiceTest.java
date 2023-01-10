package com.example.web.reactive.service;

import static org.mockito.BDDMockito.given;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private PersonService personService;
  @Mock
  private PersonRepository repository;
  private Person jordan;

  @BeforeEach
  void setUp() {
    personService = new PersonService(repository);
    jordan = new Person(1L, "Mike", "Jordan");
  }

  @Test
  void testGetById() {
    given(repository.findById(Long.getLong("1"))).willReturn(Mono.just(jordan));
    StepVerifier.create(personService.getById("1"))
        .expectNext(jordan)
        .verifyComplete();
  }

  @Test
  void testGetAll() {
    Person pip = new Person(33L, "Skottie", "Pippen");
    given(repository.findAll()).willReturn(Flux.just(jordan, pip));
    StepVerifier.create(personService.getAll())
        .expectNext(jordan)
        .expectNext(pip)
        .verifyComplete();
  }

  @Test
  void testUpdatePerson() {
    given(repository.save(jordan)).willReturn(Mono.just(jordan));
    StepVerifier.create(personService.updatePerson(jordan))
        .expectNext(jordan)
        .verifyComplete();
  }

  @Test
  void testUpdatePersonWithError() {
    jordan.setId(null);
    StepVerifier.create(personService.updatePerson(jordan))
        .expectErrorMessage("id can not be null")
        .verify();
  }
}
