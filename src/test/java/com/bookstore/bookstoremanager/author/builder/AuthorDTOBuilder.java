package com.bookstore.bookstoremanager.author.builder;

import com.bookstore.bookstoremanager.author.dto.*;
import lombok.*;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Allan Alves";

    @Builder.Default
    private final int age = 28;

    public AuthorDTO buildAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }
}
