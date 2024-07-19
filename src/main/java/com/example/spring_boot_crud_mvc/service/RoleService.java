package com.example.spring_boot_crud_mvc.service;

import com.example.spring_boot_crud_mvc.model.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String name);

    Role save(Role role);

    List<Role> findAll();

    Role findById(Integer id);
}
