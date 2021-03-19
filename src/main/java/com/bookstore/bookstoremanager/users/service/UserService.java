package com.bookstore.bookstoremanager.users.service;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;
import com.bookstore.bookstoremanager.users.exception.*;
import com.bookstore.bookstoremanager.users.mapper.*;
import com.bookstore.bookstoremanager.users.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UserService {

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public MessageDTO create(UserDTO userToCreateDTO) {
    verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
    User userToCreate = userMapper.toModel(userToCreateDTO);
    User createdUser = userRepository.save(userToCreate);

    return creationMessage(createdUser);
  }

  public void delete(Long id) {
    verifyIfExists(id);
    userRepository.deleteById(id);
  }

  private void verifyIfExists(String email, String username) {
    userRepository
        .findByEmailOrUsername(email, username)
        .ifPresent(
            user -> {
              throw new UserAlreadyExistsException(email, username);
            });
  }

  private MessageDTO creationMessage(User createdUser) {
    String createdUsername = createdUser.getUsername();
    Long createdUserId = createdUser.getId();

    String createdUserMessage =
        String.format(
            "User %s with ID %s successfully created",
            createdUser.getUsername(), createdUser.getId());

    return MessageDTO.builder().message(createdUserMessage).build();
  }

  private void verifyIfExists(Long id) {
    userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }
}
