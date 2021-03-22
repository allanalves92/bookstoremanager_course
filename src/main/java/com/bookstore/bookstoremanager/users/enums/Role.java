package com.bookstore.bookstoremanager.users.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum Role {
  ADMIN("Admin"),
  USER("user");

  private final String description;
}
