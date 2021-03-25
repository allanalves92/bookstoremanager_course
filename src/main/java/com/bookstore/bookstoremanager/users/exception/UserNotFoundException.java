package com.bookstore.bookstoremanager.users.exception;

import javax.persistence.*;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(Long id) {
    super(String.format("User with ID %s not exists!", id));
  }

  public UserNotFoundException(String username) {
    super(String.format("User with username %s not exists!", username));
  }
}
