package com.example.realworld.repository;

import com.example.realworld.entity.Follow;
import com.example.realworld.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepo extends JpaRepository<Follow, Long> {
    Optional<Follow> findByMemberAndFollower(Member member, Member follower);
}
