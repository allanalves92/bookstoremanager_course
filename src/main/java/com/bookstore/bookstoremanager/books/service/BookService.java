package com.bookstore.bookstoremanager.books.service;

import com.bookstore.bookstoremanager.author.service.*;
import com.bookstore.bookstoremanager.books.mapper.*;
import com.bookstore.bookstoremanager.books.repository.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import com.bookstore.bookstoremanager.users.service.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

  private final BookMapper bookMapper = BookMapper.INSTANCE;

  private BookRepository bookRepository;

  private UserService userService;

  private AuthorService authorService;

  private PublisherService publisherService;
}
