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

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
    return userService.create(userToCreateDTO);
  }
}
