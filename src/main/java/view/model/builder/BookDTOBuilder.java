package view.model.builder;

import view.model.BookDTO;

public class BookDTOBuilder {

    private BookDTO bookDTO;

    public BookDTOBuilder() {
        bookDTO = new BookDTO();
    }

    public BookDTOBuilder setAuthor(String author) {
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setTitle(String title) {
        bookDTO.setTitle(title);
        return this;
    }

    public BookDTOBuilder setPrice(Float price){
        bookDTO.setPrice(price);
        return this;
    }

    public BookDTOBuilder setStock(Integer stock){
        bookDTO.setStock(stock);
        return this;
    }

    public BookDTO build() {
        return bookDTO;
    }
}
