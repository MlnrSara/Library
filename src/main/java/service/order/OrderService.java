package service.order;

import model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    boolean sell(Order order);
}
