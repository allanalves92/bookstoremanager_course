package com.bookstore.bookstoremanager.user.service;

import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;
import com.bookstore.bookstoremanager.users.exception.*;
import com.bookstore.bookstoremanager.users.mapper.*;
import com.bookstore.bookstoremanager.users.repository.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUP() {
    userDTOBuilder = UserDTOBuilder.builder().build();
  }

  @Test
  void whenNewsUserIsInformedThenItShouldBeCreated() {
    UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
    User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);

    String expectedCreationMessage = "User allanalves with ID 1 successfully created";

    when(userRepository.findByEmailOrUsername(
            expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername()))
        .thenReturn(Optional.empty());

    when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

    MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

    assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
  }

  @Test
  void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
    UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
    User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);

    when(userRepository.findByEmailOrUsername(
            expectedCreatedUserDTO.getEmail(), expectedCreatedUserDTO.getUsername()))
        .thenReturn(Optional.of(expectedCreatedUser));

    assertThrows(
        UserAlreadyExistsException.class, () -> userService.create(expectedCreatedUserDTO));
  }

  @Test
  void whenValidUserIsInformedThenItShouldBeDeleted() {
    UserDTO expectedUserToBeDeletedDTO = userDTOBuilder.buildUserDTO();
    User expectedUserToBeReturned = userMapper.toModel(expectedUserToBeDeletedDTO);

    Long expectedUserIdToBeDeleted = expectedUserToBeDeletedDTO.getId();

    when(userRepository.findById(expectedUserIdToBeDeleted))
        .thenReturn(Optional.of(expectedUserToBeReturned));
    doNothing().when(userRepository).deleteById(expectedUserIdToBeDeleted);

    userService.delete(expectedUserIdToBeDeleted);

    verify(userRepository, times(1)).deleteById(expectedUserIdToBeDeleted);
    verify(userRepository, times(1)).findById(expectedUserIdToBeDeleted);
  }

  @Test
  void whenInvalidUserIdIsGivenThenAnExceptionShouldBeThrown() {
    var expectedInvalidUserId = 2L;

    when(userRepository.findById(expectedInvalidUserId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> this.userService.delete(expectedInvalidUserId));
  }
}
