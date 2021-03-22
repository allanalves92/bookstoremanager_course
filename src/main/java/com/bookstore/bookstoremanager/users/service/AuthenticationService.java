package com.bookstore.bookstoremanager.users.service;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.User;
import com.bookstore.bookstoremanager.users.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

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
