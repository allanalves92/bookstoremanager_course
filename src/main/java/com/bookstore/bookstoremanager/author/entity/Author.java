package com.bookstore.bookstoremanager.author.entity;

import com.bookstore.bookstoremanager.books.entity.*;
import com.bookstore.bookstoremanager.entity.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Author extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int age;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

}
