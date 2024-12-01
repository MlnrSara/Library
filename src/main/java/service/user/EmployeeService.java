package service.user;

import model.User;
import model.validator.Notification;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Notification<Boolean> save(String username, String password);

    List<User> findAll();

    User findByUsername(String username);

    List<User> findAllEmployees();


}
