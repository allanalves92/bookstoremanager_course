package com.bookstore.bookstoremanager.books.service;

import com.bookstore.bookstoremanager.author.service.*;
import com.bookstore.bookstoremanager.books.builder.*;
import com.bookstore.bookstoremanager.books.mapper.*;
import com.bookstore.bookstoremanager.books.repository.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  private final BookMapper bookMapper = BookMapper.INSTANCE;

  @Mock private BookRepository bookRepository;

  @Mock private UserService userService;

  @Mock private AuthorService authorService;

  @Mock private PublisherService publisherService;

  @InjectMocks private BookService bookService;

  private BookRequestDTOBuilder bookRequestDTOBuilder;

  private BookResponseDTOBuilder bookResponseDTOBuilder;

  private AuthenticatedUser authenticatedUser;

  @BeforeEach
  void setUp() {
    bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
    bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
    authenticatedUser = new AuthenticatedUser("rodrigo", "123456", "ADMIN");
  }
}
