package com.example.spring_boot_crud_mvc.dao;

import com.example.spring_boot_crud_mvc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    public void save(User user);

    public void delete(int id);

    public void update(User user);

    public Optional<User> findById(int id);

    public List<User> findAll();

}
