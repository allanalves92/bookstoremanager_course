package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController implements BookControllerDocs {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }
}
