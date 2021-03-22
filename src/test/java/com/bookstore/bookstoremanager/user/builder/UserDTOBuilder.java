package com.bookstore.bookstoremanager.user.builder;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.enums.*;
import lombok.*;

import java.time.*;

@Builder
public class UserDTOBuilder {

  @Builder.Default private final Long id = 1L;

  @Builder.Default private final String name = "Allan Alves";

  @Builder.Default private final int age = 28;

  @Builder.Default private final Gender gender = Gender.MALE;

  @Builder.Default private final String email = "allanalves_teste@gmail.com";

  @Builder.Default private final String username = "allanalves";

  @Builder.Default private final String password = "12345";

  @Builder.Default private final LocalDate birthDate = LocalDate.of(2021, 03, 18);

  @Builder.Default private final Role role = Role.USER;

  public UserDTO buildUserDTO() {
    return new UserDTO(id, name, age, gender, email, username, password, birthDate, role);
  }
}
