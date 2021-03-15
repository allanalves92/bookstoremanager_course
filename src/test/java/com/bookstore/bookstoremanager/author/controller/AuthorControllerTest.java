package com.bookstore.bookstoremanager.author.controller;

import com.bookstore.bookstoremanager.author.builder.*;
import com.bookstore.bookstoremanager.author.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
        mockMvc =
                MockMvcBuilders.standaloneSetup(authorController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
    }
}
