package com.bookstore.bookstoremanager.books.builder;

import com.bookstore.bookstoremanager.author.builder.*;
import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.publisher.builder.*;
import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.user.builder.*;
import com.bookstore.bookstoremanager.users.dto.*;
import lombok.*;

@Builder
public class BookResponseDTOBuilder {

  @Builder.Default private final Long id = 1L;

  @Builder.Default private final String name = "Spring Boot Pro";

  @Builder.Default private final String isbn = "978-3-16-148410-0";

  @Builder.Default
  private final PublisherDTO publisher = PublisherDTOBuilder.builder().build().buildPublisherDTO();

  @Builder.Default
  private final AuthorDTO author = AuthorDTOBuilder.builder().build().buildAuthorDTO();

  @Builder.Default private final Integer pages = 200;

  @Builder.Default private final Integer chapters = 10;

  private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

  public BookResponseDTO buildBookResponse() {
    return new BookResponseDTO(id, name, isbn, pages, chapters, author, publisher);
  }
}
