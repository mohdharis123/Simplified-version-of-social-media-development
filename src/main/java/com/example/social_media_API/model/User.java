package com.example.social_media_API.model;

import com.example.social_media_API.Enum.Gender;
import com.example.social_media_API.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true,nullable = false)
    String userName;

    @Column(unique = true,nullable = false)
    String password;

    @Column(unique = true,nullable = false)
    String emailId;

    @Column(unique = true,nullable = false)
    String mobNo;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String bio;

    String profilePicture;

    @OneToMany(mappedBy = "User",cascade = CascadeType.ALL)
    List<Integer> FriendIds = new ArrayList<>();

    @OneToMany(mappedBy = "User",cascade = CascadeType.ALL)
    List<UserDto> Followers = new ArrayList<>();

    @OneToMany(mappedBy = "User",cascade = CascadeType.ALL)
    List<UserDto> Followings = new ArrayList<>();

    @ManyToMany
    private List<Post> savedPost=new ArrayList<>();
}
