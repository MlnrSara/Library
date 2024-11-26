package service.order;

import model.Order;
import repository.order.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService{

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean sell(Order order) {
        return repository.save(order);
    }
}
