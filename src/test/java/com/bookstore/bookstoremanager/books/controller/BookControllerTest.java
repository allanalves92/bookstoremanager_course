package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.builder.*;
import com.bookstore.bookstoremanager.books.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

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
}
