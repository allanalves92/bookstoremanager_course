package com.bookstore.bookstoremanager.publishers.exception;

import javax.persistence.*;

public class PublisherNotFoundException extends EntityNotFoundException {

  public PublisherNotFoundException(Long id) {
    super(String.format("Publisher with id %s not exists!", id));
  }
}
