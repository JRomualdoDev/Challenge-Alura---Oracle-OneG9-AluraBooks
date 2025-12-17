package com.AluraOracleOneG9.Books.service;

import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> getAuthor(String name) {

        return authorRepository.findByName(name);

    }

}