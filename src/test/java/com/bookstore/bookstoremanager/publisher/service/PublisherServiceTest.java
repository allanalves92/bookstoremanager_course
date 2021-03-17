package com.bookstore.bookstoremanager.publisher.service;

import com.bookstore.bookstoremanager.publisher.builder.*;
import com.bookstore.bookstoremanager.publishers.dto.*;
import com.bookstore.bookstoremanager.publishers.entity.*;
import com.bookstore.bookstoremanager.publishers.exception.*;
import com.bookstore.bookstoremanager.publishers.mapper.*;
import com.bookstore.bookstoremanager.publishers.repository.*;
import com.bookstore.bookstoremanager.publishers.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

  @Test
  void whenNewPublisherIsInformedThenItShouldBeCreated() {
    PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
    Publisher expectedCreatedPublisher = publisherMapper.toModel(expectedPublisherToCreateDTO);

    when(publisherRepository.findByNameOrCode(
            expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
        .thenReturn(Optional.empty());
    when(publisherRepository.save(expectedCreatedPublisher)).thenReturn(expectedCreatedPublisher);

    PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

    assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
  }

  @Test
  void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
    PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
    Publisher expectedCreatedPublisher = publisherMapper.toModel(expectedPublisherToCreateDTO);

    when(publisherRepository.findByNameOrCode(
            expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
        .thenReturn(Optional.of(expectedCreatedPublisher));

    assertThrows(
        PublisherAlreadyExistsException.class,
        () -> publisherService.create(expectedPublisherToCreateDTO));
  }
}
