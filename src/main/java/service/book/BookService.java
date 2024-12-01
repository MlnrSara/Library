package service.book;

import model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    boolean save(Book book);

    boolean delete(Book book);

    boolean update(Book book);

    int getAgeOfBook(Long id);

}
