package com.bookstore.bookstoremanager.books.service;

import com.bookstore.bookstoremanager.author.entity.*;
import com.bookstore.bookstoremanager.author.service.*;
import com.bookstore.bookstoremanager.books.builder.*;
import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.books.entity.*;
import com.bookstore.bookstoremanager.books.exception.*;
import com.bookstore.bookstoremanager.books.mapper.*;
import com.bookstore.bookstoremanager.books.repository.*;
import com.bookstore.bookstoremanager.publishers.entity.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

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

  @Test
  void whenNewBookIsInformedThenItShouldBeCreated() {
    BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
    BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
    Book expectedCreatedBook = bookMapper.toModel(expectedCreatedBookDTO);

    when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
        .thenReturn(new User());
    when(bookRepository.findByNameAndIsbnAndUser(
            eq(expectedBookToCreateDTO.getName()),
            eq(expectedBookToCreateDTO.getIsbn()),
            any(User.class)))
        .thenReturn(Optional.empty());
    when(authorService.verifyAndGetIfExists(expectedBookToCreateDTO.getAuthorId()))
        .thenReturn(new Author());
    when(publisherService.verifyAndGetIfExists(expectedBookToCreateDTO.getPublisherId()))
        .thenReturn(new Publisher());
    when(bookRepository.save(any(Book.class))).thenReturn(expectedCreatedBook);

    BookResponseDTO createdBookResponseDTO =
        bookService.create(authenticatedUser, expectedBookToCreateDTO);

    assertThat(createdBookResponseDTO, is(equalTo(expectedCreatedBookDTO)));
  }

  @Test
  void whenExistingBookIsInformedToCreateThenAnExceptionShouldBeThrown() {
    BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
    BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
    Book expectedDuplicatedBook = bookMapper.toModel(expectedCreatedBookDTO);

    when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
        .thenReturn(new User());
    when(bookRepository.findByNameAndIsbnAndUser(
            eq(expectedBookToCreateDTO.getName()),
            eq(expectedBookToCreateDTO.getIsbn()),
            any(User.class)))
        .thenReturn(Optional.of(expectedDuplicatedBook));

    assertThrows(
        BookAlreadyExistsException.class,
        () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));
  }

  @Test
  void whenExistingBookIsInformedThenABookShouldBeReturned() {
    BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
    BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
    Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

    when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
    when(bookRepository.findByIdAndUser(
            eq(expectedBookToFindDTO.getId()),
            any(User.class))).thenReturn(Optional.of(expectedFoundBook));

    BookResponseDTO foundBookDTO = bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId());

    assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));
  }

  @Test
  void whenNotExistingBookIsInformedThenAnExceptionShouldBeThrown() {
    BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();

    when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
    when(bookRepository.findByIdAndUser(
            eq(expectedBookToFindDTO.getId()),
            any(User.class))).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId()));
  }

}
