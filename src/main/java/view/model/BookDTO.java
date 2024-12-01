package view.model;

import javafx.beans.property.*;

public class BookDTO {
    private StringProperty title;

    public void setTitle(String title){
        titleProperty().set(title);
    }

    public String getTitle(){
        return titleProperty().get();
    }

    public StringProperty titleProperty(){
        if(title == null){
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    private StringProperty author;

    public void setAuthor(String author){
        authorProperty().set(author);
    }

    public String getAuthor(){
        return authorProperty().get();
    }

    public StringProperty authorProperty(){
        if(author == null){
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }

    private FloatProperty price;

    public void setPrice(Float price){
        priceProperty().set(price);
    }

    public Float getPrice(){
        return priceProperty().get();
    }

    public FloatProperty priceProperty(){
        if (price == null) {
            price = new SimpleFloatProperty(this, "price");
        }
        return price;
    }

    private IntegerProperty stock;

    public void setStock(Integer stock){
        stockProperty().set(stock);
    }

    public Integer getStock(){
        return stockProperty().get();
    }

    public IntegerProperty stockProperty(){
        if(stock == null){
            stock = new SimpleIntegerProperty(this, "stock");
        }
        return stock;
    }
}
