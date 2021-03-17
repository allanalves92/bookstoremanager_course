package com.bookstore.bookstoremanager.publisher.controller;

import com.bookstore.bookstoremanager.publisher.builder.*;
import com.bookstore.bookstoremanager.publishers.controller.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

  private static final String PUBLISHER_API_URL_PATH = "/api/v1/publishers";

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
}
