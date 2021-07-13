package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.Role;
import com.SpringSecurity.Spring.Security.Model.User;
import com.SpringSecurity.Spring.Security.Repositories.RoleRepository;
import com.SpringSecurity.Spring.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/management/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public Role createRole(@RequestBody Role role) throws Exception {
        if(roleRepository.existsByRoleName(role.getRoleName())){
            throw new Exception(String.format("This %s role already exists",role.getRoleName()));
        }
        return roleRepository.save(role);
    }

    @PutMapping("/add/{username}")
    public User addUserRole(@PathVariable String username, @RequestBody Role role) throws Exception {
        User user;
        Role roleFound;
        if(userRepository.existsByUsername(username)){
            user = userRepository.getUserByUsername(username);
        }else
            throw new Exception(String.format("There was not a user with %s as username",username));

        if(roleRepository.existsByRoleName(role.getRoleName())){
            roleFound = roleRepository.getByRoleName(role.getRoleName());
        }else
            throw new Exception(String.format("There was not a role with %s name",role.getRoleName()));
        user.addRole(roleFound);
        return userRepository.save(user);
    }

}
