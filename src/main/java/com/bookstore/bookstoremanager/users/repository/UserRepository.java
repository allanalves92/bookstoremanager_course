package com.bookstore.bookstoremanager.users.repository;

import com.bookstore.bookstoremanager.users.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrUsername(String email, String username);
}
