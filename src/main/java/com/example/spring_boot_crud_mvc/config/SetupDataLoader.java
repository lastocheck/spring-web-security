package com.example.spring_boot_crud_mvc.config;

import com.example.spring_boot_crud_mvc.model.ContactInfo;
import com.example.spring_boot_crud_mvc.model.Role;
import com.example.spring_boot_crud_mvc.model.User;
import com.example.spring_boot_crud_mvc.service.RoleService;
import com.example.spring_boot_crud_mvc.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final RoleService roleService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        //create user and admin roles if they don't exist and return them
        List<Role> roles = Stream.of("ROLE_ADMIN", "ROLE_USER").map(roleString -> {
            Role role = roleService.findByName(roleString);
            if (role != null) {
                return role;
            }
            Role newRole = new Role();
            newRole.setName(roleString);
            roleService.save(newRole);
            return newRole;
        }).toList();

        User adminUser = new User("admin", new ContactInfo("admin@test.com", "adminphone"));
        adminUser.addRole(roles.get(0));
        adminUser.setPassword(passwordEncoder.encode("12345"));
        User regularUser =  new User("user", new ContactInfo("user@test.com", "userphone"));
        regularUser.addRole(roles.get(1));
        regularUser.setPassword(passwordEncoder.encode("12345"));

        userService.saveAll(List.of(adminUser, regularUser));

        alreadySetup = true;
    }

}
