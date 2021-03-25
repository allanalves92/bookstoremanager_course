package com.bookstore.bookstoremanager.books.controller;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.users.dto.*;
import io.swagger.annotations.*;

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
}
