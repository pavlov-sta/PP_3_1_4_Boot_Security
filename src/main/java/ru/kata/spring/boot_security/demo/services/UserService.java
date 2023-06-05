package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    User findByLogin(String login);

    List<User> getAllUsers();

    void removeUser(int id);

    void addRoleUser(User user, String role);
}
