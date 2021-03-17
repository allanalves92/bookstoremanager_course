package com.bookstore.bookstoremanager.publishers.controller;

import com.bookstore.bookstoremanager.publishers.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

  private PublisherService publisherService;

  @Autowired
  public PublisherController(PublisherService publisherService) {
    this.publisherService = publisherService;
  }
}
