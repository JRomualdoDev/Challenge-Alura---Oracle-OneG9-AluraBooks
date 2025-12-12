package com.AluraOracleOneG9.Books;

import com.AluraOracleOneG9.Books.index.Index;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import com.AluraOracleOneG9.Books.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApplication implements CommandLineRunner {

    private BookService bookService;
    private AuthorRepository authorRepository;

    public BooksApplication(BookService bookService,  AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        Index index = new Index(bookService,  authorRepository);
        index.showMenu();
    }
}
