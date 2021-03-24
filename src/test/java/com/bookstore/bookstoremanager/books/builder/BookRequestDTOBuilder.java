package com.bookstore.bookstoremanager.books.builder;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.dto.*;
import lombok.*;

@Builder
public class BookRequestDTOBuilder {

  @Builder.Default private final Long id = 1L;

  @Builder.Default private final String name = "Spring Boot Pro";

  @Builder.Default private final String isbn = "978-3-16-148410-0";

  @Builder.Default private final Long publisherId = 2L;

  @Builder.Default private final Long authorId = 3L;

  @Builder.Default private final Integer pages = 200;

  @Builder.Default private final Integer chapters = 10;

  private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

  public BookRequestDTO buildRequestBookDTO() {
    return new BookRequestDTO(id, name, isbn, pages, chapters, publisherId, authorId);
  }
}
