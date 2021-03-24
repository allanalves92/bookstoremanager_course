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

  @Mock private AuthenticationService authenticationService;

  @InjectMocks private UserController userController;

  private UserDTOBuilder userDTOBuilder;

  private MockMvc mockMvc;

  private JwtRequestBuilder jwtRequestBuilder;

  @BeforeEach
  void setUP() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    jwtRequestBuilder = JwtRequestBuilder.builder().build();
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

  @Test
  void whenPUTIsCalledThenStatusOKShouldBeReturned() throws Exception {
    UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
    Long expectedUserToUpdateId = expectedUserToUpdateDTO.getId();

    String updateUserMessage = "User allanalves with ID 1 successfully updated";

    MessageDTO updateMessage = MessageDTO.builder().message(updateUserMessage).build();

    when(userService.update(expectedUserToUpdateDTO.getId(), expectedUserToUpdateDTO))
        .thenReturn(updateMessage);

    mockMvc
        .perform(
            put(USERS_API_URL_PATH + "/" + expectedUserToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(updateMessage.getMessage())));
  }

  @Test
  void whenPUTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {

    UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
    Long expectedUserToUpdateId = expectedUserToUpdateDTO.getId();

    expectedUserToUpdateDTO.setName(null);

    mockMvc
        .perform(
            put(USERS_API_URL_PATH + "/" + expectedUserToUpdateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenPOSTIsCalledToAuthenticateUserThenOkShouldBeReturned() throws Exception {
    JwtRequest jwtRequest = jwtRequestBuilder.builJwtRequest();
    JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("fakeToken").build();

    when(authenticationService.createAuthenticationToken(jwtRequest)).thenReturn(expectedJwtToken);

    mockMvc
        .perform(
            post(USERS_API_URL_PATH + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jwtToken", is(expectedJwtToken.getJwtToken())));
  }

  @Test
  void whenPOSTIsCalledToAuthenticateUserWithoutPasswordThenBadRequestShouldBeReturned()
      throws Exception {
    JwtRequest jwtRequest = jwtRequestBuilder.builJwtRequest();
    jwtRequest.setPassword(null);

    mockMvc
        .perform(
            post(USERS_API_URL_PATH + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jwtRequest)))
        .andExpect(status().isBadRequest());
  }
}
