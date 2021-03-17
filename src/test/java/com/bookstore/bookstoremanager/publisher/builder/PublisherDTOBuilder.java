package com.bookstore.bookstoremanager.publisher.builder;

import com.bookstore.bookstoremanager.publishers.dto.*;
import lombok.*;

import java.time.*;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Editora Alves";

    @Builder.Default
    private final String code = "Allan123";

    @Builder.Default
    private LocalDate foundationDate = LocalDate.of(2021,3,16);

    public PublisherDTO buildPublisherDTO(){
      return  new PublisherDTO(id,name,code,foundationDate);
    }

}
