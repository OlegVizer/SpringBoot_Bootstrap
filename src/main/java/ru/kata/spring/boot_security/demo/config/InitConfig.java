package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class InitConfig {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitConfig(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    Role adminRole = new Role("ROLE_ADMIN");
    Role userRole = new Role("ROLE_USER");


    private final User admin = new User("admin@mail.ru", "admin", "java", "admin");
    private final User user = new User("user@mail.ru", "user", "java", "user");
    String[] roleAdmin = new String[]{"ROLE_ADMIN", "ROLE_USER"};
    String[] roleUser = new String[]{"ROLE_USER"};


    public void init() {
        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);
        userService.saveUser(admin, roleAdmin);
        userService.saveUser(user, roleUser);
    }

}
