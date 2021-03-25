package com.bookstore.bookstoremanager.books.exception;

import javax.persistence.*;

public class BookNotFoundException extends EntityNotFoundException {

  public BookNotFoundException(Long bookId) {
    super(String.format("Book with id %s not exists!", bookId));
  }
}
