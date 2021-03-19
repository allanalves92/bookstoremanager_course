package com.bookstore.bookstoremanager.users.utils;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;

public class MessageDTOUtils {

  public static MessageDTO creationMessage(User createdUser) {
    return returnMessage(createdUser, "created");
  }

  public static MessageDTO updateMessage(User updatedUser) {
    return returnMessage(updatedUser, "updated");
  }

  public static MessageDTO returnMessage(User user, String action) {
    String username = user.getUsername();
    Long userId = user.getId();

    String message = String.format("User %s with ID %s successfully %s", username, userId, action);

    return MessageDTO.builder().message(message).build();
  }
}
