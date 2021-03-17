package com.bookstore.bookstoremanager.publishers.mapper;

import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.*;

@Mapper
public interface PublisherMapper {

  PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

  Publisher toModel(PublisherDTO publisherDTO);

  PublisherDTO toDTO(Publisher publisher);
}
