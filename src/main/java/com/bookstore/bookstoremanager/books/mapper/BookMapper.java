package com.bookstore.bookstoremanager.books.mapper;

import com.bookstore.bookstoremanager.books.dto.*;
import com.bookstore.bookstoremanager.books.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  Book toModel(BookRequestDTO bookRequestDTO);

  Book toModel(BookResponseDTO bookRequestDTO);

  BookResponseDTO toDTO(Book book);
}
