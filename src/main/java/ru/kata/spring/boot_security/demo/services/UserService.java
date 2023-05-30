package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    public User findByUsername(String username);

    List<User> getAllUsers();

    User getUser(int id);

    void removeUser(int id);

    void updateUser(User user);
}
