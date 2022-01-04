package com.example.realworld.repository;

import com.example.realworld.entity.Follow;
import com.example.realworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByUserAndFollower(User user, User follower);
}
