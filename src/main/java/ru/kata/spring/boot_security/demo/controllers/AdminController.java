package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminIndex(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUser(id));
        return "admin/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @RequestParam("role") String role) {
        if (role.contains("ROLE_ADMIN")) {
            user.addRole(roleService.getByRole("ROLE_ADMIN"));
        } else if (role.contains("ROLE_USER")) {
            user.addRole(roleService.getByRole("ROLE_USER"));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User guser, ModelMap model) {
        System.out.println("Helll");
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, @RequestParam("role") String role) {
        if (role.contains("ROLE_ADMIN")) {
            user.addRole(roleService.getByRole("ROLE_ADMIN"));
        } else if (role.contains("ROLE_USER")) {
            user.addRole(roleService.getByRole("ROLE_USER"));
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }


}

