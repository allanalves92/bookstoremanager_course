package com.bookstore.bookstoremanager.author.mapper;

import com.bookstore.bookstoremanager.author.dto.*;
import com.bookstore.bookstoremanager.author.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
