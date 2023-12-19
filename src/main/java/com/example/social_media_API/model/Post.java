package com.example.social_media_API.model;

import com.example.social_media_API.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String caption;

    @Embedded
    private UserDto user;

    private String content;

    private String mediaType;

    private LocalDateTime createdAt;

    @OneToMany
    private List<Comments> comments=new ArrayList<Comments>();

    @ElementCollection
    @Embedded
    @JoinTable(name = "likeByUsers", joinColumns = @JoinColumn(name="user_id"))
    private Set<UserDto> likedByUsers= new HashSet<>();
}
