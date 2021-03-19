package com.bookstore.bookstoremanager.user.controller;

import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.controller.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.service.*;
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

  @Test
  void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
    UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();

    String createdUserMessage = "User allanalves with ID 1 successfully created";

    MessageDTO createdMessage = MessageDTO.builder().message(createdUserMessage).build();

    when(userService.create(expectedUserToCreateDTO)).thenReturn(createdMessage);

    mockMvc
        .perform(
            post(USERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message", is(createdMessage.getMessage())));
  }

  @Test
  void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed()
      throws Exception {

    UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
    expectedUserToCreateDTO.setName(null);

    mockMvc
        .perform(
            post(USERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenDeleteWithValidIdIsCalledThenNoContentShouldBeReturned() throws Exception {
    UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();

    Long expectedDeletedUserDTOId = expectedDeletedUserDTO.getId();

    doNothing().when(userService).delete(expectedDeletedUserDTOId);

    mockMvc
        .perform(
            delete(USERS_API_URL_PATH + "/" + expectedDeletedUserDTOId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
