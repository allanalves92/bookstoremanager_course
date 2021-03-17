package com.bookstore.bookstoremanager.publishers.exception;

import javax.persistence.*;

public class PublisherAlreadyExistsException extends EntityExistsException {

  public PublisherAlreadyExistsException(String name, String code) {
    super(String.format("Publisher with name %s or code %s already exists!", name, code));
  }
}
