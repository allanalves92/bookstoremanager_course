package com.bookstore.bookstoremanager.user.service;

import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.User;
import com.bookstore.bookstoremanager.users.mapper.*;
import com.bookstore.bookstoremanager.users.repository.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

  private final UserMapper userMapper = UserMapper.INSTANCE;

  @Mock private UserRepository userRepository;

  @InjectMocks private AuthenticationService authenticationService;

  private UserDTOBuilder userDTOBuilder;

  @Mock private JwtTokenManager jwtTokenManager;

  @Mock private AuthenticationManager authenticationManager;

  private JwtRequestBuilder jwtRequestBuilder;

  @BeforeEach
  void setUp() {
    userDTOBuilder = UserDTOBuilder.builder().build();
    jwtRequestBuilder = JwtRequestBuilder.builder().build();
  }

  @Test
  void whenUsernameIsInformedThenUserShouldBeReturned() {
    UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
    User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);
    SimpleGrantedAuthority expectedUserRole =
        new SimpleGrantedAuthority("ROLE_" + expectedFoundUserDTO.getRole().getDescription());
    String expectedUsername = expectedFoundUserDTO.getUsername();

    when(userRepository.findByUsername(expectedUsername))
        .thenReturn(Optional.of(expectedFoundUser));

    UserDetails userDetails = authenticationService.loadUserByUsername(expectedUsername);

    assertThat(userDetails.getUsername(), is(equalTo(expectedFoundUser.getUsername())));
    assertThat(userDetails.getPassword(), is(equalTo(expectedFoundUser.getPassword())));
    assertTrue(userDetails.getAuthorities().contains(expectedUserRole));
  }

  @Test
  void whenInvalidUsernameIsInformedThenAnExceptionShouldBeThrown() {
    UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
    String expectedUsername = expectedFoundUserDTO.getUsername();

    when(userRepository.findByUsername(expectedUsername)).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> authenticationService.loadUserByUsername(expectedUsername));
  }

  @Test
  void whenUsernameAndPasswordIsInformedThenATokenShouldBeGenerated() {
    JwtRequest jwtRequest = jwtRequestBuilder.builJwtRequest();
    UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
    User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);
    String expectedGeneratedToken = "fakeToken";

    when(userRepository.findByUsername(jwtRequest.getUsername()))
        .thenReturn(Optional.of(expectedFoundUser));

    when(jwtTokenManager.generateToken(any(UserDetails.class))).thenReturn(expectedGeneratedToken);

    JwtResponse generatedTokenResponse =
        authenticationService.createAuthenticationToken(jwtRequest);

    assertThat(generatedTokenResponse.getJwtToken(), is(equalTo(expectedGeneratedToken)));
  }
}
