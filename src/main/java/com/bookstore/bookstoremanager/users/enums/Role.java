package com.bookstore.bookstoremanager.users.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN("ADMIN"),
  USER("USER");

  private final String description;
}
