package com.bookstore.bookstoremanager.users.mapper;

import com.bookstore.bookstoremanager.users.dto.*;
import com.bookstore.bookstoremanager.users.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User toModel(UserDTO userDTO);

  UserDTO toDTO(User user);
}
