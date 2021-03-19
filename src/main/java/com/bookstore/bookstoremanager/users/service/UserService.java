package com.bookstore.bookstoremanager.users.service;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;
import com.bookstore.bookstoremanager.users.exception.*;
import com.bookstore.bookstoremanager.users.mapper.*;
import com.bookstore.bookstoremanager.users.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static com.bookstore.bookstoremanager.users.utils.MessageDTOUtils.*;

@Service
public class UserService {

  private static final UserMapper userMapper = UserMapper.INSTANCE;

  private final UserRepository userRepository;

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
    verifyAndGetIfExists(id);
    userRepository.deleteById(id);
  }

  public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
    User foundUser = verifyAndGetIfExists(id);
    userToUpdateDTO.setId(foundUser.getId());

    User userToUpdate = userMapper.toModel(userToUpdateDTO);
    userToUpdate.setCreatedDate(foundUser.getCreatedDate());

    User updatedUser = userRepository.save(userToUpdate);
    return updateMessage(updatedUser);
  }

  private void verifyIfExists(String email, String username) {
    userRepository
        .findByEmailOrUsername(email, username)
        .ifPresent(
            user -> {
              throw new UserAlreadyExistsException(email, username);
            });
  }

  private User verifyAndGetIfExists(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }
}
