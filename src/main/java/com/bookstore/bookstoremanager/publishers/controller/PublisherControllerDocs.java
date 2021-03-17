package com.bookstore.bookstoremanager.publishers.controller;

import com.bookstore.bookstoremanager.publishers.dto.*;
import io.swagger.annotations.*;

@Api("Publishers management")
public interface PublisherControllerDocs {

  @ApiOperation(value = "Publisher creation operation")
  @ApiResponses({
    @ApiResponse(code = 201, message = "Success Publisher creation "),
    @ApiResponse(
        code = 400,
        message =
            "Missing required fields, wrong field range value or publisher already registered")
  })
  PublisherDTO create(PublisherDTO publisherDTO);
}
