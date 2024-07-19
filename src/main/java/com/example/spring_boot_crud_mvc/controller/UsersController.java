package com.example.spring_boot_crud_mvc.controller;

import com.example.spring_boot_crud_mvc.model.User;
import com.example.spring_boot_crud_mvc.repository.UserRepository;
import com.example.spring_boot_crud_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/users")
public class UsersController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UsersController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "users/users";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        System.out.println("saving user " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        if (id > 0) {
            userService.delete(id);
        }
        return "redirect:/users";
    }

    @GetMapping("/update")
    public String getUpdatePage(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User newUserDetails) {
        User user = userService.findById(newUserDetails.getId());
        user.setUsername(newUserDetails.getUsername());
        user.setContactInfo(newUserDetails.getContactInfo());
        userService.update(user);
        return "redirect:/users";
    }

}
