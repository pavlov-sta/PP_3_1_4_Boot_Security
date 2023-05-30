package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.services.UserService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(UserService userService, SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/lk/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/process_login")
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

//    @Bean
//    public UserDetailsService users(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$8uBH9zuo9bg0X0n7mpIxu.8FlnsBhU2EuGaau1LSQp4mSxIp4b9ju")
//                .roles("ADMIN", "USER")
//                .build();
//        JdbcUserDetailsManager usersJdbc = new JdbcUserDetailsManager(dataSource);
//        if (usersJdbc.userExists(user.getUsername())) {
//            usersJdbc.deleteUser(user.getUsername());
//        }
//        if (usersJdbc.userExists(admin.getUsername())) {
//            usersJdbc.deleteUser(admin.getUsername());
//        }
//        usersJdbc.createUser(user);
//        usersJdbc.createUser(admin);
//        return usersJdbc;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }


}
