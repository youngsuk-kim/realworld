package com.example.realworld.entity;

import com.example.realworld.constant.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.REMOVE;

@Builder
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    public User(Long id, String password, Profile profile, Authority authority) {
        this(id, password, profile, authority, null);
    }

    public User(String password, Profile profile, Authority authority) {
        this(null, password, profile, authority, null);
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @Embedded
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "user_id"))
    @OneToMany(cascade = REMOVE)
    private Set<User> followingUsers = new HashSet<>();

    public void updateUser(String email, String userName, String image, String bio) {
        this.profile.updateUser(email, userName, image, bio);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void addFollower(User follower) {
        this.followingUsers.add(follower);
    }

    public boolean isFollowing(User follower) {
        return this.followingUsers.contains(follower);
    }

    public boolean unfollow(User follower) {
        return this.followingUsers.remove(follower);
    }

    public String getUsername() {
        return profile.getUsername();
    }

    public String getBio() {
        return profile.getBio();
    }

    public String getEmail() {
        return profile.getEmail();
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return profile.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
