package com.example.realworld.entity;

import com.example.realworld.constant.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    public User(Long id, String username, String email, String password, String bio, String image, Authority authority) {
        this(id, username, email, password, bio, image, authority, null);
    }

    public User(String username, String email, String password, String bio, String image, Authority authority) {
        this(null, username, email, password, bio, image, authority, null);
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String bio;

    private String image;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> follow = new ArrayList<>();

    public void updateUser(String email, String userName, String image, String bio) {
        if(email != null) this.email = email;
        if(userName != null) this.username = userName;
        if(image != null) this.image = image;
        if(bio != null) this.bio = bio;
    }

    public void changePassword(String password) {
        this.password = password;
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
