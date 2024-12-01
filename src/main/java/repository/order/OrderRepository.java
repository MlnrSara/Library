package repository.order;

import model.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll();

    boolean save(Order order);

}
