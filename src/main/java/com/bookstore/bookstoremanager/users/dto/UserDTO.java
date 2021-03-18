package com.bookstore.bookstoremanager.users.dto;

import com.bookstore.bookstoremanager.users.enums.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private Long id;

  @NotNull
  @NotEmpty
  @Size(max = 255)
  private String name;

  @NotNull
  @Max(120)
  private int age;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Gender gender;

  @NotNull @NotEmpty @Email private String email;

  @NotNull @NotEmpty private String username;

  @NotNull @NotEmpty private String password;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate birthDate;
}
