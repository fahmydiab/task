package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUserRepository implements UserRepository {
  private JdbcTemplate jdbc;

  @Autowired
  private PlatformTransactionManager platformTransactionManager;

  @Autowired
  public JdbcUserRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Iterable<User> findAll() {
    return jdbc.query("select id, name from User",
      this::mapRowToUser);
  }

  @Override
  public User findOne(Long id) throws SQLException {
    return jdbc.queryForObject(
      "select id, name from User where id=?",
      this::mapRowToUser, id);

  }


  @Override
  public User save(User user) {
    jdbc.update("insert into User (id, name) values (?, ?)",
      user.getId(),
      user.getName());
    return user;

  }

  @Override
  public String delete(Long id) {
    try {
      User user = findOne(id);
      jdbc.update("delete from User where id=?",
        user.getId());
    } catch (Exception e) {
      return e.getLocalizedMessage();
    }
    return "Deleted!!";
  }

  @Override
  public String deleteAllUsers() throws SQLException {
    DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();

    TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);

    int update = jdbc.update("delete from User");

    platformTransactionManager.rollback(status);
    return "Success!!";
  }

  private User mapRowToUser(ResultSet resultSet, int i) throws SQLException {
    return new User(
      resultSet.getLong("id"),
      resultSet.getString("name"));
  }
}
