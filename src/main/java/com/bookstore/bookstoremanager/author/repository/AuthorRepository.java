package com.bookstore.bookstoremanager.author.repository;

import com.bookstore.bookstoremanager.author.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
