package com.bookstore.bookstoremanager.user.controller;

import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.controller.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.web.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.servlet.view.json.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  private static final String USERS_API_URL_PATH = "/api/v1/users";

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  private UserDTOBuilder userDTOBuilder;

  private MockMvc mockMvc;

  @BeforeEach
  void setUP() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
  }
}
