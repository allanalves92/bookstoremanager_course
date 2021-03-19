package com.bookstore.bookstoremanager.users.exception;

import javax.persistence.*;

public class UserAlreadyExistsException extends EntityExistsException {

  public UserAlreadyExistsException(String email, String username) {
    super(String.format("User with email %s or username %s already exists!", email, username));
  }
}
