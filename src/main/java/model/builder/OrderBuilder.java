package model.builder;

import model.Order;

import java.time.LocalDate;

public class OrderBuilder {
    private Order order;

    public OrderBuilder(){
        order = new Order();
    }

    public OrderBuilder setId(Long id){
        order.setId(id);
        return this;
    }

    public OrderBuilder setTimestamp(LocalDate timestamp){
        order.setTimestamp(timestamp);
        return this;
    }

    public OrderBuilder setEmployeeId(Long id){
        order.setEmployeeId(id);
        return this;
    }

    public OrderBuilder setTitle(String title){
        order.setTitle(title);
        return this;
    }

    public OrderBuilder setAuthor(String author){
        order.setAuthor(author);
        return this;
    }

    public OrderBuilder setPrice(Float price){
        order.setPrice(price);
        return this;
    }

    public OrderBuilder setStock(Integer stock){
        order.setStock(stock);
        return this;
    }

    public Order build(){
        return order;
    }
}
