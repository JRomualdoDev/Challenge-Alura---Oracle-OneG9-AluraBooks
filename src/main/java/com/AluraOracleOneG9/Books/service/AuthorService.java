package com.AluraOracleOneG9.Books.service;

import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> getAuthor(String name) {

        return authorRepository.findByNameContainingIgnoreCase(name);

    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAllAuthorAliveGivenYear(int year) {

        return authorRepository.findAuthorAliveIn(year);
    }
}