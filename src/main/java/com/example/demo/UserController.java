package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController()
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users")
  public Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  public User getUserById(@PathVariable Long id) throws SQLException {
    Optional<User> user = Optional.ofNullable(userRepository.findOne(id));
    return user.get();
  }

  @PostMapping("/users")
  public User updateUser(@RequestBody User user){
    User savedUser = userRepository.save(user);
    return user;
  }

  @DeleteMapping("/users/{id}")
  public String deleteUser(@PathVariable Long id){
    String message = userRepository.delete(id);
    return message;
  }
  @DeleteMapping("/users")
  public String deleteAllUser() throws SQLException {
    String message = userRepository.deleteAllUsers();
    return message;
  }

}
