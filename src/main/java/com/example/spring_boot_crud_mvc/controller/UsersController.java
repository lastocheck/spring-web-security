package com.example.spring_boot_crud_mvc.controller;

import com.example.spring_boot_crud_mvc.model.Role;
import com.example.spring_boot_crud_mvc.model.User;
import com.example.spring_boot_crud_mvc.repository.UserRepository;
import com.example.spring_boot_crud_mvc.service.RoleService;
import com.example.spring_boot_crud_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/users")
public class UsersController {
    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UsersController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "users/users";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam List<Integer> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = roleIds.stream().map(roleService::findById).collect(Collectors.toSet());
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        if (id > 0) {
            userService.delete(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/update")
    public String getUpdatePage(@RequestParam("id") int id, Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userService.findById(id));
        return "users/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User newUserDetails, @RequestParam List<Integer> roleIds) {
        User user = userService.findById(newUserDetails.getId());
        Set<Role> roles = roleIds.stream().map(roleService::findById).collect(Collectors.toSet());
        user.setRoles(roles);
        user.setUsername(newUserDetails.getUsername());
        user.setContactInfo(newUserDetails.getContactInfo());
        userService.update(user);
        return "redirect:/admin/users";
    }

}
