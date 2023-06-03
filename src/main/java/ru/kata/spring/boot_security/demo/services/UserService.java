package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    User findByLogin(String login);

    List<User> getAllUsers();

    User getUser(int id);

    void removeUser(int id);

    void updateUser(User user);
}
