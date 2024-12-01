package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import model.Order;
import model.builder.OrderBuilder;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;

public class BookController {
    private final BookService bookService;
    private final BookView bookView;
    private final OrderService orderService;
    private final Long userId;
    public BookController(BookView bookView, BookService bookService, OrderService orderService, Long userId){
        this.bookService = bookService;
        this.bookView = bookView;
        this.orderService = orderService;
        this.userId = userId;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String price = bookView.getPrice();
            String stock = bookView.getStock();

            if(title.isEmpty() || author.isEmpty() || price.isEmpty() || stock.isEmpty() ){
                bookView.addDisplayAlertMessage("Save Error", "Problem at input fields", "Cannot have an empty input field.");
            } else {
                try {
                    BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setPrice(Float.parseFloat(price)).setStock(Integer.parseInt(stock)).build();
                    boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                    if(savedBook){
                        bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                        bookView.addBookToObservableList(bookDTO);
                    } else {
                        bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding a book to the database. Please try again!");
                    }
                } catch (NumberFormatException e) {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at Price or Stock fields", "Cannot have characters different from numbers in those fields.");
                }

            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                if(bookDTO.getPrice()>0) {
                    Book book = BookMapper.convertBookDTOToBook(bookDTO);
                    Order order = new OrderBuilder().setEmployeeId(userId).setTimestamp(LocalDate.now()).setAuthor(book.getAuthor()).setTitle(book.getTitle()).setPrice(book.getPrice()).setStock(1).build();
                    boolean sellSuccessful = orderService.sell(order);
                    book.setStock(book.getStock() - 1);
                    bookService.update(book);
                    if (sellSuccessful) {
                        bookView.addDisplayAlertMessage("Sold Successfully", "Book Sold", "The book was sold.");
                        bookDTO.setStock(bookDTO.getStock() - 1);
                    } else {
                        bookView.addDisplayAlertMessage("Sell Error", "Problem at selling Book", "There was a problem with the database... Please Try again!");
                    }
                } else {
                    bookView.addDisplayAlertMessage("Sell Error", "Problem at selling Book", "There are no more books in stock!");
                }
            } else {
                bookView.addDisplayAlertMessage("Sell Error", "Problem at selling book", "You must select a book before pressing the sell button.");

            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null){
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "The book was removed from the database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting Book", "There was a problem with the database... Please Try again!");
                }
            } else {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");

            }
        }
    }
}
