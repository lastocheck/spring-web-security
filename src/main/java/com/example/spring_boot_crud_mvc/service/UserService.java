package com.example.spring_boot_crud_mvc.service;

import com.example.spring_boot_crud_mvc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    void delete(int id);

    void update(User user);

    User findById(int id);

    Optional<User> findByUsername(String username);

    List<User> getAllUsers();

    List<User> saveAll(List<User> users);

}
