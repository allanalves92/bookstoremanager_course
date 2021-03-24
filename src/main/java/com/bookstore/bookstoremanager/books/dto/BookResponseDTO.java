package com.bookstore.bookstoremanager.books.dto;

import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.publishers.dto.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

  private Long id;

  private String name;

  private String isbn;

  private Integer pages;

  private Integer chapters;

  private AuthorDTO author;

  private PublisherDTO publisher;
}
