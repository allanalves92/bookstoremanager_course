package com.bookstore.bookstoremanager.publisher.controller;

import com.bookstore.bookstoremanager.publisher.builder.*;
import com.bookstore.bookstoremanager.publishers.controller.*;
import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

import java.util.*;

import static com.bookstore.bookstoremanager.utils.JsonConversionUtils.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

  private static final String PUBLISHERS_API_URL_PATH = "/api/v1/publishers";

  @Mock private PublisherService publisherService;

  @InjectMocks private PublisherController publisherController;

  private MockMvc mockMvc;

  private PublisherDTOBuilder publisherDTOBuilder;

  @BeforeEach
  void setUP() {
    publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    mockMvc =
        MockMvcBuilders.standaloneSetup(publisherController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }

  @Test
  void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
    PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    when(publisherService.create(expectedCreatedPublisherDTO))
        .thenReturn(expectedCreatedPublisherDTO);

    mockMvc
        .perform(
            post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
        .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
        .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
  }

  @Test
  void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed()
      throws Exception {
    PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    expectedCreatedPublisherDTO.setName(null);

    mockMvc
        .perform(
            post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenGETWithValidIdIsCalledThenStatusOkShouldBeReturned() throws Exception {
    PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    when(publisherService.findById(expectedCreatedPublisherDTO.getId()))
        .thenReturn(expectedCreatedPublisherDTO);

    mockMvc
        .perform(
            get(PUBLISHERS_API_URL_PATH + "/" + expectedCreatedPublisherDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
        .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
        .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
  }

  @Test
  void whenGETListIsCalledThenStatusOKShouldBeReturned() throws Exception {
    PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

    when(publisherService.findAll())
        .thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

    mockMvc
        .perform(get(PUBLISHERS_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(expectedCreatedPublisherDTO.getId().intValue())))
        .andExpect(jsonPath("$[0].name", is(expectedCreatedPublisherDTO.getName())))
        .andExpect(jsonPath("$[0].code", is(expectedCreatedPublisherDTO.getCode())));
  }
}
