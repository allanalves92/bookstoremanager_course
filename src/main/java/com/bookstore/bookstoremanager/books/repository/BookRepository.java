package com.bookstore.bookstoremanager.books.repository;

import com.bookstore.bookstoremanager.books.entity.*;
import org.springframework.data.jpa.repository.*;

public interface BookRepository extends JpaRepository<Book, Long> {
}
