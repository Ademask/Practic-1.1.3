package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "create table if not exists users (id int auto_increment primary key , name varchar(45), last_name varchar(45), age int(3));";
        try (Connection connection = getConnection(); Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE if exists users";
        try (Connection connection = getConnection(); Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, last_name, age FROM users";

        try (Connection connection = getConnection(); Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = getConnection(); Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("connection failed");
        }
    }
}
