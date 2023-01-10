package com.example.web.reactive.service;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository repository;

  @Transactional(readOnly = true)
  public Mono<Person> getById(final String id) {
    return repository.findById(Long.getLong(id));
  }

  @Transactional(readOnly = true)
  public Flux<Person> getAll() {
    return repository.findAll();
  }

  @Transactional
  public Mono<Person> updatePerson(final Person person) {
    if (person.getId() == null) {
      log.error("Invalid id!");
      return Mono.error(new IllegalArgumentException("id can not be null"));
    } else {
      log.info("Updating {}", person);
      return repository.save(person);
    }
  }
}
