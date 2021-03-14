package com.bookstore.bookstoremanager.users.repository;

import com.bookstore.bookstoremanager.users.entity.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {
}
