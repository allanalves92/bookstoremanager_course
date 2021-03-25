package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.books.service.*;
import com.bookstore.bookstoremanager.users.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

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

  @GetMapping("/{bookId}")
  public BookResponseDTO findByIdAndUser(
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long bookId) {
    return bookService.findByIdAndUser(authenticatedUser, bookId);
  }

  @GetMapping
  public List<BookResponseDTO> findAllByUser(
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
    return bookService.findAllByUser(authenticatedUser);
  }

  @DeleteMapping("/{bookId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteByIdAndUser(
      @AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable Long bookId) {
    bookService.deleteByIdAndUser(authenticatedUser, bookId);
  }
}
