package com.AluraOracleOneG9.Books;

import com.AluraOracleOneG9.Books.index.Index;
import com.AluraOracleOneG9.Books.service.AuthorService;
import com.AluraOracleOneG9.Books.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApplication implements CommandLineRunner {

    private BookService bookService;
    private AuthorService authorService;

    public BooksApplication(BookService bookService,  AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        Index index = new Index(bookService, authorService);
        index.showMenu();
    }
}
