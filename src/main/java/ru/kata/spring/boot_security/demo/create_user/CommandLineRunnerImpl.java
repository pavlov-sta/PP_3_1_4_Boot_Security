package ru.kata.spring.boot_security.demo.create_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public CommandLineRunnerImpl(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        User admin = new User("admin", "admin", "123", (byte) 35, "admin@mail.ru");
        User user = new User("user", "user", "123", (byte) 45, "user@gmail.com");
        admin.addRole(roleAdmin);
        user.addRole(roleUser);

        userService.add(admin);
        userService.add(user);
    }
}
