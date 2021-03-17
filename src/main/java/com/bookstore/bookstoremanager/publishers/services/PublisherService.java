package com.bookstore.bookstoremanager.publishers.services;

import com.bookstore.bookstoremanager.publishers.mapper.*;
import com.bookstore.bookstoremanager.publishers.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PublisherService {

  private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

  private PublisherRepository publisherRepository;

  @Autowired
  public PublisherService(PublisherRepository publisherRepository) {
    this.publisherRepository = publisherRepository;
  }

}
