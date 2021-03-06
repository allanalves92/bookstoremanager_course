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
import org.springframework.transaction.annotation.*;

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

  @Transactional
  public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
    User foundAuthenticatedUser =
        userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
    Book foundBookToDelete = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
    bookRepository.deleteByIdAndUser(foundBookToDelete.getId(), foundAuthenticatedUser);
  }

  public BookResponseDTO updateByIdAndUser(
      AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO) {
    User foundAuthenticatedUser =
        userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
    Book foundBook = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
    Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
    Publisher foundPublisher =
        publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

    Book bookToUpdate = bookMapper.toModel(bookRequestDTO);
    bookToUpdate.setId(foundBook.getId());
    bookToUpdate.setUser(foundAuthenticatedUser);
    bookToUpdate.setAuthor(foundAuthor);
    bookToUpdate.setPublisher(foundPublisher);
    bookToUpdate.setCreatedDate(foundBook.getCreatedDate());
    Book updatedBook = bookRepository.save(bookToUpdate);
    return bookMapper.toDTO(updatedBook);
  }

  private Book verifyAndGetIfExists(Long bookId, User foundAuthenticatedUser) {
    return bookRepository
        .findByIdAndUser(bookId, foundAuthenticatedUser)
        .orElseThrow(() -> new BookNotFoundException(bookId));
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
