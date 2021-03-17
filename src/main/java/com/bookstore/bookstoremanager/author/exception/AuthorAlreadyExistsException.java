package com.bookstore.bookstoremanager.author.exception;

import javax.persistence.*;

public class AuthorAlreadyExistsException extends EntityExistsException {

  public AuthorAlreadyExistsException(String name) {
    super(String.format("User with name %s already exists!", name));
  }
}
