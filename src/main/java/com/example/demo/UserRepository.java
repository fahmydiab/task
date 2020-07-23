package com.example.demo;


import java.sql.SQLException;

public interface UserRepository {
  Iterable<User> findAll();
  User findOne(Long id) throws SQLException;
  User save(User user);
  String delete(Long id);
  String deleteAllUsers() throws SQLException;

}
