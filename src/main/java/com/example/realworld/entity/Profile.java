package com.example.realworld.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    public Profile(String username, String email) {
        this(username, email, null, null, false);
    }

    public Profile(String username, String bio, String image, boolean following) {
        this(username, null, bio, image, following);
    }

    private String username;

    private String email;

    private String bio;

    private String image;

    @Transient
    private boolean following;

    public boolean isFollowing() {
        return following;
    }

    public void updateUser(String email, String userName, String image, String bio) {
        if(email != null) this.email = email;
        if(userName != null) this.username = userName;
        if(image != null) this.image = image;
        if(bio != null) this.bio = bio;
    }
}
