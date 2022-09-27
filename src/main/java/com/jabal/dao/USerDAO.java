package com.jabal.dao;

import java.util.List;

import com.jabal.model.User;

public interface UserDao {
    void save(User user);
    List<User> selectAllUsers();
    User selectUser(int id);
    void deleteUser(int id);
}
