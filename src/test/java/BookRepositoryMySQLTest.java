import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryMySQL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMySQLTest {

    private static BookRepository bookRepository;

    @BeforeAll
    public static void setUp(){
        //I did not set up a LibraryTest database, so I won't be using it (hence the false)
        bookRepository = new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(false).getConnection());
    }

    @Test
    public void findAll(){
        List<Book> books = bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void findById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void save(){
        assertTrue(bookRepository.save(new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 20))
                .build()));
    }

    @Test
    public void delete(){
        bookRepository.save(new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 20))
                .build());
        List<Book> books = bookRepository.findAll();
        if(!books.isEmpty()){
            Book book = books.getFirst();
            assertTrue(bookRepository.delete(book));
        } else {
            System.out.println("We don't have books in the DB!");
            assertTrue(false);
        }
    }
}
