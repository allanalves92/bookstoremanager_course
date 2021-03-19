package com.bookstore.bookstoremanager.users.exception;

import javax.persistence.*;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(Long id) {
    super(String.format("User with ID %s not exists!", id));
  }
}
