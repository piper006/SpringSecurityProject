package com.SpringSecurity.Spring.Security.Repositories;

import com.SpringSecurity.Spring.Security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User getUserByUsername(String username);
    boolean existsByUsername(String username);
}
