package com.SpringSecurity.Spring.Security.Controllers;

import com.SpringSecurity.Spring.Security.Model.MyUserDetails;
import com.SpringSecurity.Spring.Security.Model.Role;
import com.SpringSecurity.Spring.Security.Model.User;
import com.SpringSecurity.Spring.Security.Repositories.RoleRepository;
import com.SpringSecurity.Spring.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception(String.format("This %s username is already taken", user.getUsername()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/info")
    public User retrieveUser(@AuthenticationPrincipal MyUserDetails caller) {
        User noPassUser = userRepository.getUserByUsername(caller.getUsername());
        noPassUser.setPassword("");
        return noPassUser;
    }

    @PutMapping("/change/password")
    public User editUser(@AuthenticationPrincipal MyUserDetails caller, @RequestBody User edited) {
        caller.getUser().changePassword(edited.getPassword());
        return userRepository.save(caller.getUser());
    }

    @GetMapping("/management/info/{username}")
    public User retrieveAnyUserByUsername(@PathVariable String username) {
        User noPassUser = userRepository.getUserByUsername(username);
        noPassUser.setPassword("");
        return noPassUser;
    }


}
