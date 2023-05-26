package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;


@Controller
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDaoIml) {
        this.userDao = userDaoIml;
    }

    @GetMapping("/")
    public String printWelcome(ModelMap model) {
        model.addAttribute("users", userDao.getAllUsers());
        return "index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user) {
        userDao.add(user);
        return "redirect:/";
    }

    @GetMapping("user/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.getUser(id));
        return "edit";
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable("id") int id){
        userDao.removeUser(id);
        return "redirect:/";
    }

    @PatchMapping("/user/{id}")
    public String update(@ModelAttribute("user")@Valid User user){
        userDao.updateUser(user);
        return "redirect:/";
    }
}
