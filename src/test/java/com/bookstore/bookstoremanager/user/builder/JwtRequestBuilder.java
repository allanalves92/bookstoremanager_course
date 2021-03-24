package com.bookstore.bookstoremanager.user.builder;

import com.bookstore.bookstoremanager.users.dto.*;
import lombok.*;

@Builder
public class JwtRequestBuilder {

  @Builder.Default private String username = "rodrigo";

  @Builder.Default private String password = "123456";

  public JwtRequest builJwtRequest() {
    return new JwtRequest(username, password);
  }
}
