package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.builder.*;
import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.books.service.*;
import com.bookstore.bookstoremanager.users.dto.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

import static com.bookstore.bookstoremanager.utils.JsonConversionUtils.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

  private static final String BOOKS_API_URL_PATH = "/api/v1/books";

  @Mock private BookService bookService;

  @InjectMocks private BookController bookController;

  private MockMvc mockMvc;

  private BookRequestDTOBuilder bookRequestDTOBuilder;

  private BookResponseDTOBuilder bookResponseDTOBuilder;

  @BeforeEach
  void setUp() {
    bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
    bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
    mockMvc =
        MockMvcBuilders.standaloneSetup(bookController)
            .addFilters()
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }

  @Test
  void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
    BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
    BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();

    when(bookService.create(any(AuthenticatedUser.class), eq(expectedBookToCreateDTO)))
        .thenReturn(expectedCreatedBookDTO);

    mockMvc
        .perform(
            post(BOOKS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBookToCreateDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
        .andExpect(jsonPath("$.name", is(expectedCreatedBookDTO.getName())))
        .andExpect(jsonPath("$.isbn", is(expectedCreatedBookDTO.getIsbn())));
  }

  @Test
  void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestShouldBeReturned() throws Exception {
    BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
    expectedBookToCreateDTO.setIsbn(null);

    mockMvc
        .perform(
            post(BOOKS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedBookToCreateDTO)))
        .andExpect(status().isBadRequest());
  }
}
