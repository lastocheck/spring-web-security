package com.example.spring_boot_crud_mvc.service;

import com.example.spring_boot_crud_mvc.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void delete(int id);

    void update(User user);

    User findById(int id);

    List<User> getAllUsers();

    List<User> saveAll(List<User> users);

}
