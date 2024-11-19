import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import service.book.BookService;
import service.book.BookServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello World");

        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 20))
                .build();
        System.out.println(book);

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        bookService.save(book);
        System.out.println(bookService.findAll());
        Book luckyWindmill = new BookBuilder().setAuthor("Ioan Slavici")
                                .setTitle("Moara cu noroc")
                                .setPublishedDate(LocalDate.of(1914, 11, 15))
                                .build();
        bookService.save(luckyWindmill);
        System.out.println(bookService.findAll());
        bookService.delete(luckyWindmill);
        System.out.println(bookService.findAll());
        bookService.delete(book);
    }
}
