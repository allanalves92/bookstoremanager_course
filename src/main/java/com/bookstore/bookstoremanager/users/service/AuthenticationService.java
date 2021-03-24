package com.bookstore.bookstoremanager.users.service;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.User;
import com.bookstore.bookstoremanager.users.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenManager jwtTokenManager;

  public JwtResponse createAuthenticationToken(JwtRequest jwtRequest) {
    String username = jwtRequest.getUsername();
    String password = jwtRequest.getPassword();

    authenticate(username, password);

    UserDetails userDetails = this.loadUserByUsername(username);

    String token = jwtTokenManager.generateToken(userDetails);

    return JwtResponse.builder().jwtToken(token).build();
  }

  private void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> {
                  throw new UsernameNotFoundException(
                      String.format("User not found with username %s", username));
                });

    return new AuthenticatedUser(
        user.getUsername(), user.getPassword(), user.getRole().getDescription());
  }
}
