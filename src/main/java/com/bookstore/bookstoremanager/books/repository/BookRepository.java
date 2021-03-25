package com.bookstore.bookstoremanager.books.repository;

import com.bookstore.bookstoremanager.books.entity.*;
import com.bookstore.bookstoremanager.users.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Object> findByNameAndIsbnAndUser(String name, String isbn, User foundUser);

  Optional<Book> findByIdAndUser(Long id, User user);

  List<Book> findAllByUser(User user);

  void deleteByIdAndUser(Long id, User foundAuthenticatedUser);
}
