package com.bookstore.bookstoremanager.users.controller;

import com.bookstore.bookstoremanager.users.dto.*;
import io.swagger.annotations.*;

@Api("Users management")
public interface UserControllerDocs {

  @ApiOperation(value = "User creation operation")
  @ApiResponses({
    @ApiResponse(code = 201, message = "Success User creation"),
    @ApiResponse(
        code = 400,
        message = "Missing required fields, wrong field range value or User already registered")
  })
  MessageDTO create(UserDTO userToCreateDTO);

  @ApiOperation(value = "Delete User by id operation")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Success User deleted"),
    @ApiResponse(code = 404, message = "User not found error code")
  })
  void delete(Long id);

  @ApiOperation(value = "Update user operation")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success User update"),
    @ApiResponse(
        code = 400,
        message = "Missing required fields, wrong field range value or User already registered")
  })
  MessageDTO update(Long id, UserDTO userToUpdateDTO);

  @ApiOperation(value = "User authentication operation")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success user authenticated"),
    @ApiResponse(code = 404, message = "User not found")
  })
  JwtResponse createAuthenticationToken(JwtRequest jwtRequest);
}
