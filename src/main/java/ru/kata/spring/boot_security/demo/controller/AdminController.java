package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

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

    @GetMapping
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("role", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/new")
    public String getAddNewUserPage(@AuthenticationPrincipal User user, Model model) {
        User newUser = new User();
        model.addAttribute("user", user);
        model.addAttribute("newUser", newUser);
        model.addAttribute("role", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/saveUser")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "select_role", required = false) String[] roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @PutMapping("edit/{id}")
    public String updateUserById(@ModelAttribute("user") User user, Model model,
                                 @RequestParam(value = "select_role", required = false) String[] roles) {
        model.addAttribute("user", user);
        model.addAttribute("role", roleService.getAllRoles());
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

}
