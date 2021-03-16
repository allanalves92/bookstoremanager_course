package com.bookstore.bookstoremanager.author.controller;

import com.bookstore.bookstoremanager.author.builder.*;
import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.author.service.*;
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
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

  private static final String AUTHOR_API_URL_PATH = "/api/v1/authors";

  @Mock private AuthorService authorService;

  @InjectMocks private AuthorController authorController;

  private MockMvc mockMvc;

  private AuthorDTOBuilder authorDTOBuilder;

  @BeforeEach
  void setUp() {
    authorDTOBuilder = AuthorDTOBuilder.builder().build();
    mockMvc =
        MockMvcBuilders.standaloneSetup(authorController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }

  @Test
  void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
    AuthorDTO expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();

    when(authorService.create(expectedCreatedAuthorDTO)).thenReturn(expectedCreatedAuthorDTO);

    mockMvc
        .perform(
            post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedAuthorDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(expectedCreatedAuthorDTO.getId().intValue())))
        .andExpect(jsonPath("$.name", is(expectedCreatedAuthorDTO.getName())))
        .andExpect(jsonPath("$.age", is(expectedCreatedAuthorDTO.getAge())));
  }

  @Test
  void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeInformed() throws Exception {
    AuthorDTO expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    expectedCreatedAuthorDTO.setName(null);

    mockMvc
        .perform(
            post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedAuthorDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenGETwithValidIdIsCalledThenStatusOkShouldBeReturned() throws Exception {
    AuthorDTO expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();

    when(authorService.findById(expectedCreatedAuthorDTO.getId()))
        .thenReturn(expectedCreatedAuthorDTO);

    mockMvc
        .perform(
            get(AUTHOR_API_URL_PATH + "/" + expectedCreatedAuthorDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(expectedCreatedAuthorDTO.getId().intValue())))
        .andExpect(jsonPath("$.name", is(expectedCreatedAuthorDTO.getName())))
        .andExpect(jsonPath("$.age", is(expectedCreatedAuthorDTO.getAge())));
  }
}
