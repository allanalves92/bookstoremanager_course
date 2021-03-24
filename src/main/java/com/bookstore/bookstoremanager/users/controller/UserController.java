package com.bookstore.bookstoremanager.users.controller;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController implements UserControllerDocs {

  private final UserService userService;

  private AuthenticationService authenticationService;

  @Autowired
  public UserController(UserService userService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
    return userService.create(userToCreateDTO);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }

  @PutMapping("/{id}")
  public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) {
    return userService.update(id, userToUpdateDTO);
  }

  @PostMapping(value = "/authenticate")
  public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
    return authenticationService.createAuthenticationToken(jwtRequest);
  }
}
