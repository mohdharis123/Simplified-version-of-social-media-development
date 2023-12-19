package com.example.social_media_API.model;

import com.example.social_media_API.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name="comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @NotNull
    @AttributeOverride(name="id",column = @Column(name="user_id"))
    private UserDto userDto;

    @NotNull
    private String content;

    @ElementCollection
    @Embedded
    private Set<UserDto> likedByUsers= new HashSet<>();

    private LocalDateTime createdAt;
}
