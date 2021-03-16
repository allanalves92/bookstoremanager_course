package com.bookstore.bookstoremanager.author.controller;

import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.author.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements AuthorControllerDocs {

  private AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
    return authorService.create(authorDTO);
  }

  @GetMapping("/{id}")
  public AuthorDTO findById(@PathVariable Long id) {
    return authorService.findById(id);
  }

  @GetMapping
  public List<AuthorDTO> findAll() {
    return authorService.findAll();
  }

}
