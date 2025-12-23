package com.AluraOracleOneG9.Books.index;

import com.AluraOracleOneG9.Books.DTO.BookDTO;
import com.AluraOracleOneG9.Books.model.ApiGutendexData;
import com.AluraOracleOneG9.Books.model.Author;
import com.AluraOracleOneG9.Books.model.Book;
import com.AluraOracleOneG9.Books.service.ApiConsumption;
import com.AluraOracleOneG9.Books.service.AuthorService;
import com.AluraOracleOneG9.Books.service.BookService;
import com.AluraOracleOneG9.Books.service.ConvertData;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Index {

    private BookService bookService;
    private AuthorService authorService;

    public Index(BookService bookService,  AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    Scanner sc = new Scanner(System.in);
    private final String baseUrl = "https://gutendex.com/books";
    private ConvertData convert =  new ConvertData();

    public void showMenu() {

        while (true) {
            System.out.println("Choose the number of your choice: ");
            System.out.println("1- Search for a book by title: ");
            System.out.println("2- List all registered books:");
            System.out.println("3- List registered books by title:");
            System.out.println("4- List all registered authors: ");
            System.out.println("5- List registered authors by name: ");
            System.out.println("6- List authors who were alive in a given year");
            System.out.println("7- List books in a specific language");
            System.out.println("0- exit");

            int option = sc.nextInt();

            switch (option) {
                case 0:
                    return;
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listAllRegisteredBooks();
                    break;
                case 3:
                    listRegisteredBooksByTitle();
                    break;
                case 4:
                    listAllRegisteredAuthors();
                    break;
                case 5:
                    listRegisteredAuthorsByName();
                    break;
                case 6:
                    listAuthorsWhoWereAliveInGivenYear();
                    break;
                case 7:
                    listBooksSpecificLanguage();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
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

        System.out.println("\n" + "*".repeat(30) + "\n");

        if (bookApi.books().isEmpty()) {
            System.out.println("Search not found!!!");
            System.out.println("\n" + "*".repeat(30) + "\n");
            return;
        }

        boolean bookSaved = bookService.saveBook(bookApi);

        if (bookSaved) {
            System.out.println("The book has been saved successfully!");
        } else  {
            System.out.println("Error saving book!");
        }

        System.out.println("\n" + "*".repeat(30) + "\n");
    }

    private void listAllRegisteredBooks() {

        sc.nextLine();
        System.out.println("Enter how many books do you want to list or digit 0 for all books: ");
        int numBooks = sc.nextInt();

        List<BookDTO> books = bookService.getAllBooks(numBooks);

        if (!books.isEmpty()) {
            System.out.println("*".repeat(30));
            System.out.println(formatBooks(books));
            System.out.println("*".repeat(30));
        } else  {
            System.out.println("*".repeat(30));
            System.out.println("Books empty");
            System.out.println("*".repeat(30));
        }
    }

    private void listRegisteredBooksByTitle() {

        sc.nextLine();
        System.out.println("Enter the title: ");
        String title = sc.nextLine();

        List<Book> bookList = bookService.getBook(title);

        if (!bookList.isEmpty()) {
            System.out.println("*".repeat(30));
            System.out.println(bookList);
            System.out.println("*".repeat(30));
        } else  {
            System.out.println("*".repeat(30));
            System.out.println("Book not found!");
            System.out.println("*".repeat(30));
        }
    }

    private void listAllRegisteredAuthors() {

        List<Author> authors = authorService.getAllAuthors();

        formatAuthors(authors);
    }

    private void listRegisteredAuthorsByName() {
        sc.nextLine();
        System.out.println("Enter the name of the author: ");
        String name = sc.nextLine();

        Optional<Author> author = authorService.getAuthor(name);

        if (author.isPresent()) {
            StringBuilder sb = new StringBuilder();

            System.out.println("");
            sb.append("==========================================\n");
            sb.append(String.format("%-25s | %s\n", "AUTOR", "PERÍODO"));
            sb.append("------------------------------------------\n");

            String periodo = String.format("%d - %d", author.get().getBirthYear(), author.get().getDeathYear());
            sb.append(String.format("%-25s | %s\n", author.get().getName(), periodo));

            sb.append("==========================================");

            System.out.println(sb.toString());
            System.out.println("");
        } else  {
            System.out.println("*".repeat(30));
            System.out.println("Author not found!");
            System.out.println("*".repeat(30));
        }

    }

    private void listAuthorsWhoWereAliveInGivenYear() {
        sc.nextLine();
        System.out.println("Enter the year: ");
        int year = sc.nextInt();

        List<Author> authors = authorService.getAllAuthorAliveGivenYear(year);

        formatAuthors(authors);
    }

    private void listBooksSpecificLanguage() {
        sc.nextLine();

        System.out.println("Enter the acronym of the language for search the book: ");
        String language = sc.nextLine();

        List<BookDTO> books = bookService.getBookSpecificLanguage(language.toLowerCase());
        System.out.println(formatBooks(books));
    }

    private String formatBooks(List<BookDTO> books) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n==================== BOOKS ====================\n");

        books.forEach(book -> {

            sb.append("📘 Título: ").append(book.title()).append("\n");

            sb.append("✍ Autores:\n");
            book.authors().forEach(author ->
                    sb.append("   - ").append(author).append("\n")
            );

            sb.append("📚 Idiomas: ").append(book.languages()).append("\n");
            sb.append("📥 Downloads: ").append(book.downloadCount()).append("\n");
            sb.append("© Copyright: ").append(book.copyright()).append("\n");

            sb.append("🗂 Assuntos:\n");
            book.subjects().forEach(subject ->
                    sb.append("   - ").append(subject).append("\n")
            );

            sb.append("📁 Bookshelves:\n");
            book.bookshelves().forEach(bs ->
                    sb.append("   - ").append(bs).append("\n")
            );

            sb.append("🔗 Formats:\n");
            book.formats().forEach((key, value) ->
                    sb.append("   ").append(key).append(" -> ").append(value).append("\n")
            );

            if (!book.summaries().isEmpty()) {
                sb.append("📝 Summary:\n");
                book.summaries().forEach(summary ->
                        sb.append("   ").append(summary).append("\n")
                );
            }

            sb.append("------------------------------------------------\n");
        });

        return sb.toString();
    }

    private void formatAuthors(List<Author> authors) {
        StringBuilder sb = new StringBuilder();

        System.out.println("");
        sb.append("==========================================\n");
        sb.append(String.format("%-25s | %s\n", "AUTOR", "PERÍODO"));
        sb.append("------------------------------------------\n");

        for (Author author : authors) {
            String periodo = String.format("%d - %d", author.getBirthYear(), author.getDeathYear());

            sb.append(String.format("%-25s | %s\n", author.getName(), periodo));
        }

        sb.append("==========================================");

        System.out.println(sb.toString());
        System.out.println("");
    }

}
