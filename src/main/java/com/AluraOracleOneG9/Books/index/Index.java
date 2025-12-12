package com.AluraOracleOneG9.Books.index;

import com.AluraOracleOneG9.Books.model.ApiGutendexData;
import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.model.Book;
import com.AluraOracleOneG9.Books.model.DataAuthor;
import com.AluraOracleOneG9.Books.repository.AuthorRepository;
import com.AluraOracleOneG9.Books.service.ApiConsumption;
import com.AluraOracleOneG9.Books.service.BookService;
import com.AluraOracleOneG9.Books.service.ConvertData;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Index {

    private BookService bookService;
    private AuthorRepository authorRepository;

    public Index(BookService bookService, AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    Scanner sc = new Scanner(System.in);
    private final String baseUrl = "https://gutendex.com/books";
    private ConvertData convert =  new ConvertData();

    public void showMenu() {

        System.out.println("Choose the number of your choice: ");
        System.out.println("1- Search for a book by title: ");
        System.out.println("2- List registered books:");
        System.out.println("3- List registered authors: ");
        System.out.println("4- List authors who are alive in a given year");
        System.out.println("5- List books in a specific language");
        System.out.println("0- exit");

        int option = sc.nextInt();

        switch (option) {
            case 1:
                searchBookByTitle();
                break;
        }
    }

    private void searchBookByTitle() {

        sc.nextLine();
        System.out.println("Enter the title: ");
        String title = sc.nextLine();

        // They must be separated by a space (i.e. %20 in URL-encoded format) and are case-insensitive.
        title = title.replaceAll("\\s+", "%20");

        ApiConsumption apiConsumption = new ApiConsumption();
        String json = apiConsumption.getData(baseUrl + "?search=" + title + "&sort=ascending");

        ApiGutendexData bookApi = convert.convertDataToObject(json, ApiGutendexData.class);

        boolean bookSaved = bookService.saveBook(bookApi);

        if (bookSaved) {
            System.out.println("*".repeat(30));
            System.out.println("The book has been saved successfully!");
            System.out.println("*".repeat(30));
        } else  {
            System.out.println("*".repeat(30));
            System.out.println("Error saving book!");
            System.out.println("*".repeat(30));
        }
    }
}
