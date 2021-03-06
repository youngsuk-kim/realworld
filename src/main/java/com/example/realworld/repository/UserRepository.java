package com.example.realworld.repository;

import com.example.realworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByProfileEmail(String email);
    Optional<User> findFirstByProfileEmail(String email);
    Optional<User> findFirstByProfileUsername(String username);
}
