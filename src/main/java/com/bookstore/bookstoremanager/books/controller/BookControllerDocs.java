package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.users.dto.*;
import io.swagger.annotations.*;

import java.util.*;

@Api("Books module management")
public interface BookControllerDocs {

  @ApiOperation(value = "Book creation operation")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Success book creation"),
        @ApiResponse(
            code = 400,
            message =
                "Missing required fields, wrong field range value or book already registered on system")
      })
  BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO);

  @ApiOperation(value = "Book find by id and user operation")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success book found"),
        @ApiResponse(code = 404, message = "Book not found error")
      })
  BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId);

  @ApiOperation(value = "List all books by a specific authenticated user")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Book list found by authenticated user informed")
      })
  List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser);

  @ApiOperation(value = "Book delete operation")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "Book by user successfully deleted"),
        @ApiResponse(code = 404, message = "Book not found error")
      })
  void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId);
}
