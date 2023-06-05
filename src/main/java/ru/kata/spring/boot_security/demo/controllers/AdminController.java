package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

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
    public String adminIndex(ModelMap model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        model.addAttribute("authUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("modelUser", new User());
        return "admin/admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @RequestParam("role") String role) {
        userService.addRoleUser(user, role);
        return "redirect:/admin";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, @RequestParam("role") String role) {
        userService.addRoleUser(user, role);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}

