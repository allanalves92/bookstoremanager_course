package com.bookstore.bookstoremanager.publishers.controller;

import com.bookstore.bookstoremanager.publishers.dto.*;
import io.swagger.annotations.*;

import java.util.*;

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

  @ApiOperation(value = "Find publisher by id operation")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Success publisher found"),
    @ApiResponse(code = 404, message = "Publisher not found error code")
  })
  PublisherDTO findById(Long id);

  @ApiOperation(value = "List all registered publishers")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Return all registered publishers"),
  })
  List<PublisherDTO> findAll();

  @ApiOperation(value = "Delete publisher by id operation")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Success publisher deleted"),
    @ApiResponse(code = 404, message = "Publisher not found error code")
  })
  void delete(Long id);
}
