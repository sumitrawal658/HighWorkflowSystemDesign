package com.highload.service;

import com.highload.model.Role;

import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> findRoleByName(String name);
    boolean roleExistsByName(String name);
}