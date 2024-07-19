package com.example.spring_boot_crud_mvc.service;

import com.example.spring_boot_crud_mvc.model.Role;

public interface RoleService {

    Role findByName(String name);

    Role save(Role role);
}
