package com.example.dao;

import com.example.connection.DBConnection;
import com.example.entity.User;
import com.example.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.example.exceptions.ExceptionMessage.*;

public class UserDAO implements BaseDAO<User>{
    final static String INSERT_USER = "INSERT registrationprojectdb.user(login, password, mail) VALUES (?, ?, ?)";
    final static String UPDATE_USER = "UPDATE registrationprojectdb.user SET login = ?, password = ?, mail = ? WHERE Id = ?";
    final static String DELETE_USER = "DELETE FROM registrationprojectdb.user WHERE Id = ? AND login = ? AND password = ? AND mail = ?";
    final static String FIND_USER = "SELECT * FROM registrationprojectdb.user WHERE ID = ?";
    final static String FIND_USER_BY_LOGIN = "SELECT * FROM registrationprojectdb.user WHERE login = ?";
    final static String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM registrationprojectdb.user WHERE login = ? AND password = ?";
    final static String FIND_ALL_USERS = "SELECT * FROM registrationprojectdb.user";


    @Override
    public boolean add(User user) {
        int rows = 0;
        int id = 0;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getMail());


            rows = preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            System.out.printf("User %s is successfully added! Id = %d\n", user.getLogin(), id);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return rows!=0;
    }

    @Override
    public boolean update(User user) {
        int id = user.getId();
        User oldUser = findById(id);
        if (id != 0) {
            if (user.equals(oldUser))
                System.out.printf("User '%s' (id = %d) does not require updating\n", user.getLogin(),
                        user.getId());
            else {
                try (Connection connection = DBConnection.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getMail());
                    preparedStatement.setInt(4, user.getId());

                    preparedStatement.executeUpdate();

                    System.out.printf("User %d %s is successfully updated. New person is: \n", oldUser.getId(), oldUser.getLogin());
                    System.out.println(user);

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return id != 0;
    }

    @Override
    public boolean delete(User user) {
        int rows = 0;

        if (user.getId() == 0) System.out.println("User is not found");
        else {
            try (Connection connection = DBConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getMail());

                rows = preparedStatement.executeUpdate();
                System.out.printf("User %s (id = %d) is successfully deleted!\n", user.getLogin(), user.getId());

            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

        return rows!=0;
    }

    @Override
    public boolean deleteById(int id) {
        int rows = 0;
        User user = findById(id);
        delete(user);
        return rows!=0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public User findById(int id) {
        User user = new User();
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User(id, resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4));
            } else {
                System.out.println("User is not found.");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()){
                userList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4)));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public boolean isThereLogin(String login){
        boolean result = true;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                result = false;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public User findByLoginAndPassword(String login, String password) throws DaoException {
        User user = new User();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4));
            }
        } catch (ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (SQLException e) {
            throw new DaoException(DAO_EXCEPTION_MESSAGE, e);
        }
        return user;
    }

}
