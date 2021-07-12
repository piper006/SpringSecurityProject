package com.SpringSecurity.Spring.Security.Repositories;

import com.SpringSecurity.Spring.Security.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role getByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
