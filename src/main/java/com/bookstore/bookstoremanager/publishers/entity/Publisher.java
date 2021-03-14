package com.bookstore.bookstoremanager.publishers.entity;

import com.bookstore.bookstoremanager.books.entity.*;
import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Data
@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String code;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate foundationDate;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;
}
