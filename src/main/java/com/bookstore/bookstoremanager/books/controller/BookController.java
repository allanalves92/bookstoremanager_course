package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.books.service.*;
import com.bookstore.bookstoremanager.users.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController implements BookControllerDocs {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponseDTO create(
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
      @RequestBody @Valid BookRequestDTO bookRequestDTO) {
    return bookService.create(authenticatedUser, bookRequestDTO);
  }
}
