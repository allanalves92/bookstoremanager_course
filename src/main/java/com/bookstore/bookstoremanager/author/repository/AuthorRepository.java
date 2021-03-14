package com.bookstore.bookstoremanager.author.repository;

import com.bookstore.bookstoremanager.author.entity.*;
import org.springframework.data.jpa.repository.*;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
