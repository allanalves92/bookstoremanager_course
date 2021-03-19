package com.bookstore.bookstoremanager.author.service;

import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.author.entity.*;
import com.bookstore.bookstoremanager.author.exception.*;
import com.bookstore.bookstoremanager.author.mapper.*;
import com.bookstore.bookstoremanager.author.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class AuthorService {

  private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public AuthorDTO create(AuthorDTO authorDTO) {
    verifyIfExists(authorDTO.getName());
    Author authorToCreate = authorMapper.toModel(authorDTO);
    Author createdAuthor = authorRepository.save(authorToCreate);
    return authorMapper.toDTO(createdAuthor);
  }

  public AuthorDTO findById(Long id) {
    Author foundAuthor = verifyAndGetAuthor(id);
    return authorMapper.toDTO(foundAuthor);
  }

  public List<AuthorDTO> findAll() {
    return authorRepository.findAll().stream()
        .map(authorMapper::toDTO)
        .collect(Collectors.toList());
  }

  private void verifyIfExists(String authorName) {
    authorRepository
        .findByName(authorName)
        .ifPresent(
            author -> {
              throw new AuthorAlreadyExistsException(authorName);
            });
  }

  public void delete(Long id) {
    verifyAndGetAuthor(id);
    authorRepository.deleteById(id);
  }

  private Author verifyAndGetAuthor(Long id) {
    return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
  }
}
