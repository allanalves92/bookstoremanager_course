package com.bookstore.bookstoremanager.publisher.service;

import com.bookstore.bookstoremanager.publisher.builder.*;
import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.mapper.*;
import com.bookstore.bookstoremanager.publishers.repository.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

@ExtendWith({MockitoExtension.class})
public class PublisherServiceTest {

  private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

  @Mock private PublisherRepository publisherRepository;

  @InjectMocks private PublisherService publisherService;

  private PublisherDTOBuilder publisherDTOBuilder;

  @BeforeEach
  void setUp() {
    publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    PublisherDTO publisherDTO = publisherDTOBuilder.buildPublisherDTO();
  }

}
