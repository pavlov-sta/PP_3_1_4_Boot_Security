package ru.kata.spring.boot_security.demo.create_user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommandLineRunnerImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        // пароль 123
        User admin = new User("admin", "$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju", "oppp@mail.ru");
        User user = new User("user", "$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju", "mmamm@gma.com");
        admin.addRole(roleAdmin);
        user.addRole(roleUser);

        userRepository.save(admin);
        userRepository.save(user);

    }
}
