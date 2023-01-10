package com.example.web.reactive.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  private Long id;
  private String firstName;
  private String lastName;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Person person = (Person) obj;
    return Objects.equals(id, person.id);
  }

  @Override
  public int hashCode() {
    return Person.class.hashCode();
  }
}
