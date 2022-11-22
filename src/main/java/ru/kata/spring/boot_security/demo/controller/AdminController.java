package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String showAddNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("role", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/saveUser")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "select_role", required = false) String[] roles) {
        Set<Role> role = new HashSet<>();
        role.add(roleService.getAllRoles().get(1));
        for (String s : roles) {
            if (s.equals("ROLE_ADMIN")) {
                role.add(roleService.getAllRoles().get(0));
            }
        }
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("role", roleService.getAllRoles());
        return "/edit";
    }

    @PatchMapping("edit/{id}")
    public String updateUserById(@ModelAttribute("user") User user,
                                 @PathVariable("id") int id, Model model,
                                 @RequestParam(value = "select_role", required = false) String[] roles) {
        model.addAttribute("user", user);
        Set<Role> role = new HashSet<>();
        role.add(roleService.getAllRoles().get(1));
        for (String s : roles) {
            if (s.equals("ROLE_ADMIN")) {
                role.add(roleService.getAllRoles().get(0));
            }
        }
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
