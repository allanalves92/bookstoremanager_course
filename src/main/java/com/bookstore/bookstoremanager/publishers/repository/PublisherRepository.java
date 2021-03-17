package com.bookstore.bookstoremanager.publishers.repository;

import com.bookstore.bookstoremanager.publishers.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

  Optional<Publisher> findByNameOrCode(String publisherName, String code);
}
