package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.MyUserDetails;
import com.SpringSecurity.Spring.Security.Model.Role;
import com.SpringSecurity.Spring.Security.Model.User;
import com.SpringSecurity.Spring.Security.Repositories.RoleRepository;
import com.SpringSecurity.Spring.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/register")
    public User registerNewUser(@RequestBody User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.existsByUsername(user.getUsername())){
            throw new Exception(String.format("This %s username is already taken",user.getUsername()));
        }
        return userRepository.save(user);
    }
    @GetMapping("/{username}")
    public User retrieveUser(@AuthenticationPrincipal MyUserDetails owner, @PathVariable String username) throws Exception {
        User noPassUser = new User();
        if(owner.getUsername().equals(username) || owner.isAdmin()) {
            noPassUser = userRepository.getUserByUsername(username);
            noPassUser.setPassword("");
        }else
            throw new Exception("Your are not the same person");
        return noPassUser;
    }


    @PutMapping("/add/role/{username}")
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
