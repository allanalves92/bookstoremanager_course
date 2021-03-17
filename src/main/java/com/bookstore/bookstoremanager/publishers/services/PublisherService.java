package com.bookstore.bookstoremanager.publishers.services;

import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.entity.*;
import com.bookstore.bookstoremanager.publishers.exception.*;
import com.bookstore.bookstoremanager.publishers.mapper.*;
import com.bookstore.bookstoremanager.publishers.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PublisherService {

  private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

  private PublisherRepository publisherRepository;

  @Autowired
  public PublisherService(PublisherRepository publisherRepository) {
    this.publisherRepository = publisherRepository;
  }

  public PublisherDTO create(PublisherDTO publisherDTO) {
    verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
    Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
    Publisher createdPublisher = publisherRepository.save(publisherToCreate);
    return publisherMapper.toDTO(createdPublisher);
  }

  public PublisherDTO findById(Long id) {
    Publisher publisher = verifyAndGetPublisher(id);
    return publisherMapper.toDTO(publisher);
  }

  private void verifyIfExists(String name, String code) {
    publisherRepository
        .findByNameOrCode(name, code)
        .ifPresent(
            publisher -> {
              throw new PublisherAlreadyExistsException(name, code);
            });
  }

  private Publisher verifyAndGetPublisher(Long id) {
    return publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException(id));
  }
}
