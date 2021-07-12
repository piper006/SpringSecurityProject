package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.Role;
import com.SpringSecurity.Spring.Security.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/register")
    public Role createRole(@RequestBody Role role) throws Exception {
        if(roleRepository.existsByRoleName(role.getRoleName())){
            throw new Exception(String.format("This %s role already exists",role.getRoleName()));
        }
        return roleRepository.save(role);
    }
}
