package com.bookstore.bookstoremanager.publishers.repository;

import com.bookstore.bookstoremanager.publishers.entity.*;
import org.springframework.data.jpa.repository.*;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
