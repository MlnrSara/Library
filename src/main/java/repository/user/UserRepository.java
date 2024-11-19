package repository.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserRepository {

    Notification<List<User>> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    Notification<Boolean> save(User user);

    void removeAll();

    boolean existsByUsername(String username);
}
