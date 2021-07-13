package com.SpringSecurity.Spring.Security.Model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;
    @Column (nullable = false,unique = true,name = "username")
    private String username;
    @Column (nullable = false,name = "password")
    private String password;
    @Column (nullable = false,unique = true,name = "email")
    private String email;
    @Column (nullable = false,name = "is_enabled")
    private boolean isEnabled;
    @Column (name = "last_login")
    private Date lastLogin;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable( name = "user_roles",joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(UUID userId, String username, String password, String email, boolean isEnabled, Date lastLogin, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isEnabled = isEnabled;
        this.lastLogin = lastLogin;
        this.roles = roles;
    }

    public User(UUID userId, String username, String password, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) { this.roles.add(role);}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void changePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        this.password = passwordEncoder.encode(password);
    }
}
