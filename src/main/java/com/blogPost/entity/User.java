package com.blogPost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String userName;

    private String password;

//    @Column(unique=true)
//    private String email;

    @Column(nullable = true, length = 34, name = "profile_picture")
    private String profilePictureName;

    @Transient
    public String getPhotosImagePath(){
        if(profilePictureName == null) return null;

//        return "/photos"+"/"+profilePictureName;

        return "/photos/Emmanuel/myself.jpg";
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="author", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<BlogPost> blogPosts;

    @OneToMany(mappedBy="author", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                '}';
    }

}


