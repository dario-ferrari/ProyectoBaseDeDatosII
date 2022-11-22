package com.proyecto.cart.security.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.cart.security.entities.Role;
import com.proyecto.cart.security.enums.RoleList;

public interface RoleRepository extends JpaRepository <Role, Integer> {
    Optional<Role> findByRoleName(RoleList roleName);
    
}
