package com.bookstore.bookstoremanager.books.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

  private Long id;

  @NotNull
  @NotEmpty
  @Size(max = 255)
  private String name;

  @NotNull @ISBN private String isbn;

  @NotNull
  @Max(3000)
  private Integer pages;

  @NotNull
  @Max(100)
  private Integer chapters;

  @NotNull private Long authorId;

  @NotNull private Long publisherId;
}
