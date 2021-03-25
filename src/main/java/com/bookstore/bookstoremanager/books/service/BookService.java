package com.bookstore.bookstoremanager.books.service;

import com.bookstore.bookstoremanager.author.entity.*;
import com.bookstore.bookstoremanager.author.service.*;
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
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

  private final BookMapper bookMapper = BookMapper.INSTANCE;

  private final BookRepository bookRepository;

  private final UserService userService;

  private final AuthorService authorService;

  private final PublisherService publisherService;

  public BookResponseDTO create(
      AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {

    User foundAuthenticatedUser =
        userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
    verifyIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

    Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());

    Publisher foundPublisher =
        publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

    Book bookToSave = bookMapper.toModel(bookRequestDTO);
    bookToSave.setUser(foundAuthenticatedUser);
    bookToSave.setAuthor(foundAuthor);
    bookToSave.setPublisher(foundPublisher);
    Book savedBook = bookRepository.save(bookToSave);

    return bookMapper.toDTO(savedBook);
  }

  public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
    User foundAuthenticatedUser =
        userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
    return bookRepository
        .findByIdAndUser(bookId, foundAuthenticatedUser)
        .map(bookMapper::toDTO)
        .orElseThrow(() -> new BookNotFoundException(bookId));
  }

  public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
    User foundAuthenticatedUser =
        userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
    return bookRepository.findAllByUser(foundAuthenticatedUser).stream()
        .map(bookMapper::toDTO)
        .collect(Collectors.toList());
  }

  private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
    bookRepository
        .findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
        .ifPresent(
            duplicatedBook -> {
              throw new BookAlreadyExistsException(
                  bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
            });
  }
}
