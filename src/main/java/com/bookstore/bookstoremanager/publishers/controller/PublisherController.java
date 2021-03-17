package com.bookstore.bookstoremanager.publishers.controller;

import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController implements PublisherControllerDocs {

  private PublisherService publisherService;

  @Autowired
  public PublisherController(PublisherService publisherService) {
    this.publisherService = publisherService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) {
    return publisherService.create(publisherDTO);
  }

  @GetMapping("/{id}")
  public PublisherDTO find(@PathVariable Long id) {
    return publisherService.findById(id);
  }
}
