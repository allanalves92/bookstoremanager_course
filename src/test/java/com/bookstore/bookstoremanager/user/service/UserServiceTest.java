package com.bookstore.bookstoremanager.user.service;

import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.mapper.*;
import com.bookstore.bookstoremanager.users.repository.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;

  private UserDTOBuilder userDTOBuilder;

  @BeforeEach
  void setUP() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    UserDTO userDTO = userDTOBuilder.buildUserDTO();
  }
}
