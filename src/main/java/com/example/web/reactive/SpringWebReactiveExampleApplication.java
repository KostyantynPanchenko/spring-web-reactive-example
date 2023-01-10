package com.example.web.reactive;

import com.example.web.reactive.model.Person;
import com.example.web.reactive.repository.PersonRepository;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class SpringWebReactiveExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringWebReactiveExampleApplication.class, args);
  }

  @ConditionalOnProperty(name = "executeCommandLineRunner", havingValue = "true")
  @Bean
  public CommandLineRunner commandLineRunner(DatabaseClient databaseClient, PersonRepository personRepository) {
    return (String[] args) -> {
      // demo of r2dbc SPI, just for educational purposes

      databaseClient.sql("DROP TABLE IF EXISTS person;").then().block();

      databaseClient.sql("""
          CREATE TABLE IF NOT EXISTS person(
            id SERIAL,
            first_name VARCHAR(64),
            last_name VARCHAR(64),
            CONSTRAINT PERSON_PK PRIMARY KEY(id)
          );
          """).then().block();

      databaseClient.sql("DELETE FROM person WHERE 1=1").then().block();

      personRepository.saveAll(List.of(
          new Person(null, "Michael", "Jordan"),
          new Person(null, "Scottie", "Pippen"),
          new Person(null, "Horace", "Grunt")
      )).blockLast();

      Flux<Person> jordan = databaseClient.sql("SELECT id, first_name, last_name FROM person")
          .map(row -> new Person(row.get("id", Long.class), row.get("first_name", String.class),
              row.get("last_name", String.class)))
          .all().delayElements(Duration.ofMillis(500));
      log.info("Processing...");
      jordan.subscribe(person -> log.info(person.toString()));
    };
  }
}
