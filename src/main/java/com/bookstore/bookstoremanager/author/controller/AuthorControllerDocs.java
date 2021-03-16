
package com.bookstore.bookstoremanager.author.controller;

import com.bookstore.bookstoremanager.author.dto.*;

import io.swagger.annotations.*;

@Api("Authors management")
public interface AuthorControllerDocs {

	@ApiOperation(value = "Author creation operation")
	@ApiResponses(value = { @ApiResponse(code = 201,
		message = "Success author creation"), @ApiResponse(code = 400,
			message = "Missing required fields, wrong field range value " +
				"or author already registered") })
	AuthorDTO create(AuthorDTO authorDTO);
}
