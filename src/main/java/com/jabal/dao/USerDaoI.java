package com.jabal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jabal.model.User;

public class USerDaoI implements UserDao {
    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
            + " (?, ?, ?);";                
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    @Override
    public void save(User user) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        if (user.getId() > 0) { // Update
            try (PreparedStatement statement = con.prepareStatement(UPDATE_USERS_SQL);) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getCountry());
                statement.setInt(4, user.getId());
                statement.executeUpdate();
                System.out.println("updated User:" + user.getId());
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else { // Create
            try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getCountry());
                preparedStatement.executeUpdate();
                System.out.println("Created User:" + user.getName());
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public User selectUser(int id) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }
        
        User user = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
                System.out.println("user is " + user.getId());
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return Collections.emptyList();
        }

        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_USERS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void deleteUser(int id) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try (PreparedStatement statement = con.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("User Deleted who's ID is " + id);

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
