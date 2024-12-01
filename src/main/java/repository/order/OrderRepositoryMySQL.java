package repository.order;

import model.Order;
import model.builder.OrderBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryMySQL implements OrderRepository{

    private final Connection connection;

    public OrderRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM `order`;";
        List<Order> orders = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                orders.add(getOrderFromResultSet(resultSet));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean save(Order order) {
        String newSql = "INSERT INTO `order` VALUES(null, ?, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setDate(1, java.sql.Date.valueOf(order.getTimestamp()));
            preparedStatement.setLong(2, order.getEmployeeId());
            preparedStatement.setString(3, order.getTitle());
            preparedStatement.setString(4, order.getAuthor());
            preparedStatement.setFloat(5, order.getPrice());
            preparedStatement.setInt(6, order.getStock());

            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException{
        return new OrderBuilder().setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setTimestamp(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setEmployeeId(resultSet.getLong("employee_id"))
                .setPrice(resultSet.getFloat("price"))
                .setStock(resultSet.getInt("stock"))
                .build();
    }
}
