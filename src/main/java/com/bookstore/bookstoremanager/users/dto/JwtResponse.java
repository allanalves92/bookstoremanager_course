package com.bookstore.bookstoremanager.users.dto;

import lombok.*;

@Getter
@Builder
public class JwtResponse {

  private final String jwtToken;
}
