package com.bookstore.bookstoremanager.author.service;

import com.bookstore.bookstoremanager.author.builder.*;
import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.author.entity.*;
import com.bookstore.bookstoremanager.author.exception.*;
import com.bookstore.bookstoremanager.author.mapper.*;
import com.bookstore.bookstoremanager.author.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

  private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

  @Mock private AuthorRepository authorRepository;

  @InjectMocks private AuthorService authorService;

  private AuthorDTOBuilder authorDTOBuilder;

  @BeforeEach
  void setUp() {
    authorDTOBuilder = AuthorDTOBuilder.builder().build();
  }

  @Test
  void whenNewAuthorIsInformedThenItShouldBeCreated() {
    AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
    Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

    // When
    when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
    when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
        .thenReturn(Optional.empty());

    // Then
    AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

    assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
  }

  @Test
  void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {
    AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
    Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

    // When
    when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
        .thenReturn(Optional.of(expectedCreatedAuthor));

    assertThrows(
        AuthorAlreadyExistsException.class, () -> authorService.create(expectedAuthorToCreateDTO));
  }

  @Test
  void whenValidIdIsGivenThenAnAuthorShouldBeReturned() {
    AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    Author expectedCreatedAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

    when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
        .thenReturn(Optional.of(expectedCreatedAuthor));

    AuthorDTO foundAuthorDTO = this.authorService.findById(expectedFoundAuthorDTO.getId());

    assertThat(foundAuthorDTO, is(equalTo(expectedFoundAuthorDTO)));
  }

  @Test
  void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
    AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

    when(authorRepository.findById(expectedFoundAuthorDTO.getId())).thenReturn(Optional.empty());

    assertThrows(
        AuthorNotFoundException.class,
        () -> this.authorService.findById(expectedFoundAuthorDTO.getId()));
  }

  @Test
  void whenListAuthorsIsCalledThenItShouldBeReturned() {
    AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    Author expectedCreatedAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

    when(authorRepository.findAll()).thenReturn(Collections.singletonList(expectedCreatedAuthor));

    List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

    assertThat(foundAuthorsDTO.size(), is(1));
    assertThat(foundAuthorsDTO.get(0), is(equalTo(expectedFoundAuthorDTO)));
  }

  @Test
  void whenListAuthorsIsCalledThenAnEmptyListShouldBeReturned() {
    when(authorRepository.findAll()).thenReturn(Collections.emptyList());

    List<AuthorDTO> foundAuthorsDTO = authorService.findAll();

    assertThat(foundAuthorsDTO.size(), is(0));
  }

  @Test
  void whenValidAuthorIdIsGivenThenItShouldBeDeleted() {
    AuthorDTO expectedDeletedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    Author expectedDeletedAuthor = authorMapper.toModel(expectedDeletedAuthorDTO);

    Long expectedDeletedAuthorId = expectedDeletedAuthor.getId();

    doNothing().when(authorRepository).deleteById(expectedDeletedAuthorId);

    when(authorRepository.findById(expectedDeletedAuthorId))
        .thenReturn(Optional.of(expectedDeletedAuthor));

    authorService.delete(expectedDeletedAuthorId);

    verify(authorRepository, times(1)).deleteById(expectedDeletedAuthorId);
    verify(authorRepository, times(1)).findById(expectedDeletedAuthorId);
  }

  @Test
  void whenInvalidAuthorIdIsGivenThenAnExceptionShouldBeThrown() {
    var expectedInvalidAuthorId = 2L;

    when(authorRepository.findById(expectedInvalidAuthorId)).thenReturn(Optional.empty());

    assertThrows(
        AuthorNotFoundException.class, () -> this.authorService.delete(expectedInvalidAuthorId));
  }
}
