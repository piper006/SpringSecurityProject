package com.SpringSecurity.Spring.Security.Services;

import com.SpringSecurity.Spring.Security.Model.MyUserDetails;
import com.SpringSecurity.Spring.Security.Model.User;
import com.SpringSecurity.Spring.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        return new MyUserDetails(user);
    }
}
