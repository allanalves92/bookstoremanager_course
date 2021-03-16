package com.bookstore.bookstoremanager.publishers.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.validation.constraints.*;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

  private Long id;

  @NotNull
  @NotEmpty
  @Size(max = 255)
  private String name;

  @NotNull
  @NotEmpty
  @Size(max = 50)
  private String code;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate foundationDate;
}
