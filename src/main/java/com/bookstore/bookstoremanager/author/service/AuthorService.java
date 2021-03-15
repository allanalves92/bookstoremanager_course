package com.bookstore.bookstoremanager.author.service;

import com.bookstore.bookstoremanager.author.mapper.*;
import com.bookstore.bookstoremanager.author.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }



}
