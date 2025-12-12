package com.AluraOracleOneG9.Books.service;

import com.AluraOracleOneG9.Books.model.ApiGutendexData;
import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.model.Book;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import com.AluraOracleOneG9.Books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,  AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public boolean saveBook(ApiGutendexData bookApi) {

        try {
            bookApi.books().forEach(book -> {

                Optional<Book> existBook = bookRepository.findByTitle(book.title());

                System.out.println(existBook);

                if (existBook.isEmpty()) {
                    Set<Author> managedAuthors = book.authors().stream()
                            .map(aut -> {
                                return authorRepository.findByName(aut.name())
                                        .orElseGet(() -> {
                                            Author novo = new Author(
                                                    aut.name(),
                                                    aut.birth_year(),
                                                    aut.death_year()
                                            );
                                            return authorRepository.save(novo);
                                        });
                            })
                            .collect(Collectors.toSet());

                    Book newBook = new Book();
                    newBook.setTitle(book.title());
                    newBook.setAuthors(managedAuthors);
                    newBook.setSummaries(book.summaries());
                    newBook.setSubjects(book.subjects());
                    newBook.setBookshelves(book.bookshelves());
                    newBook.setLanguages(book.languages());
                    newBook.setCopyright(book.copyright());
                    newBook.setFormats(book.formats());

                    bookRepository.save(newBook);
                }
            });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
