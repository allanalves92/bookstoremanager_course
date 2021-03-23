package com.bookstore.bookstoremanager.users.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

  @NotNull @NotEmpty private String username;

  @NotNull @NotEmpty private String password;
}
