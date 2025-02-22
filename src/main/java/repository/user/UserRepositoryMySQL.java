package repository.user;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<List<User>> findAll() {
        Notification<List<User>> findAllNotification = new Notification<>();
        List<User> usersList = new ArrayList<>();
        //if there are no users in the database, is that an error? I do not think so, but who knows...
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from `" + USER + "`;");

            ResultSet usersResultSet = preparedStatement.executeQuery();
            while (usersResultSet.next()) {
                usersList.add(getUserFromResultSet(usersResultSet));
            }
            findAllNotification.setResult(usersList);
        } catch (SQLException e) {
            e.printStackTrace();
            findAllNotification.addError("Something went wrong with the Database!");
            return findAllNotification;
        }

        return findAllNotification;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {

        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from `" + USER + "` where `username`= ?  and `password`= ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet userResultSet = preparedStatement.executeQuery();
            if (userResultSet.next())
            {
                User user = getUserFromResultSet(userResultSet);

                findByUsernameAndPasswordNotification.setResult(user);
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                return findByUsernameAndPasswordNotification;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }

        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<Boolean> save(User user) {
        Notification<Boolean> saveUserNotification = new Notification<>();
        if(!existsByUsername(user.getUsername())) {
            try {
                PreparedStatement insertUserStatement = connection
                        .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                insertUserStatement.setString(1, user.getUsername());
                insertUserStatement.setString(2, user.getPassword());
                insertUserStatement.executeUpdate();

                ResultSet rs = insertUserStatement.getGeneratedKeys();
                if(rs.next()) {
                    long userId = rs.getLong(1);
                    user.setId(userId);

                    rightsRolesRepository.addRolesToUser(user, user.getRoles());
                    saveUserNotification.setResult(true);
                } else {
                    saveUserNotification.addError("Something went wrong while saving the user. Please try again!");
                    saveUserNotification.setResult(false);
                    return saveUserNotification;
                }

                return saveUserNotification;
            } catch (SQLException e) {
                e.printStackTrace();
                saveUserNotification.setResult(false);
                saveUserNotification.addError("Something went wrong with the Database!");
            }
        }
        saveUserNotification.setResult(false);
        return saveUserNotification;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement("Select * from `" + USER + "` where `username`= ?;");

            statement.setString(1, email);
            ResultSet userResultSet = statement.executeQuery();
            return userResultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByUsername(String username){
        try{
            PreparedStatement statement = connection.prepareStatement("Select * from `" + USER + "` where `username`= ?;");

            statement.setString(1, username);
            ResultSet userResultSet = statement.executeQuery();
            if(userResultSet.next()) {
                User user = getUserFromResultSet(userResultSet);
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAllEmployees(){
        try{
            String sql = "SELECT * FROM user INNER JOIN `user_role` ON user.id = user_role.user_id WHERE user_role.role_id = 2;";
            Statement statement = connection.createStatement();
            ResultSet usersResultSet = statement.executeQuery(sql);
            List<User> usersList = new ArrayList<>();
            while (usersResultSet.next()) {
                usersList.add(getUserFromResultSet(usersResultSet));
            }
            return usersList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException{
        return new UserBuilder()
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .setId(resultSet.getLong("id"))
                .build();
    }

}