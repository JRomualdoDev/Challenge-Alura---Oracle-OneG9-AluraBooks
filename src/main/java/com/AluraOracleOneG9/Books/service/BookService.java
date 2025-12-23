package com.AluraOracleOneG9.Books.service;

import com.AluraOracleOneG9.Books.DTO.BookDTO;
import com.AluraOracleOneG9.Books.model.ApiGutendexData;
import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.model.Book;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import com.AluraOracleOneG9.Books.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,  AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public boolean saveBook(ApiGutendexData bookApi) {

        try {
            bookApi.books().forEach(book -> {

                Optional<Book> existBook = bookRepository.findFirstByTitle(book.title());

                if (existBook.isEmpty()) {
                    Set<Author> managedAuthors = book.authors().stream()
                            .map(aut -> {
                                return authorRepository.findByNameContainingIgnoreCase(aut.name())
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
                    newBook.setSummaries(new HashSet<>(book.summaries()));
                    newBook.setSubjects(new HashSet<>(book.subjects()));
                    newBook.setBookshelves(new HashSet<>(book.bookshelves()));
                    newBook.setLanguages(new HashSet<>(book.languages()));
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

    public List<Book> getBook(String title) {

        return bookRepository.findByTitleContaining(title);

    }

    public List<BookDTO> getAllBooks(int numBooks) {

        if (numBooks <= 0) return new ArrayList<>();;

        List<BookDTO> books = bookRepository.findAll(PageRequest.of(0, numBooks))
                .map(
                        book -> new BookDTO(
                                book.getTitle(),
                                book.getSummaries(),
                                book.getSubjects(),
                                book.getBookshelves(),
                                book.getLanguages(),
                                book.isCopyright(),
                                book.getMediaTypes(),
                                book.getDownloadCount(),
                                book.getFormats(),
                                book.getAuthors()
                                        .stream()
                                        .map(Author::getName)
                                        .collect(Collectors.toSet())
                        )
                ).toList();

        return books;


    }

    public List<BookDTO> getBookSpecificLanguage(String language) {

        List<BookDTO> bookDTOS = bookRepository.findAllByLanguages(language)
                .stream()
                .map(
                        book -> new BookDTO(
                                book.getTitle(),
                                book.getSummaries(),
                                book.getSubjects(),
                                book.getBookshelves(),
                                book.getLanguages(),
                                book.isCopyright(),
                                book.getMediaTypes(),
                                book.getDownloadCount(),
                                book.getFormats(),
                                book.getAuthors()
                                        .stream()
                                        .map(Author::getName)
                                        .collect(Collectors.toSet())
                        )
                ).toList();

        return bookDTOS;
    }
}
