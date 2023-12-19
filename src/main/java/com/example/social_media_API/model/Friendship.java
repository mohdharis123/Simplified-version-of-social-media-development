package com.example.social_media_API.model;

import com.example.social_media_API.Enum.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name="friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendshipId;

    @ManyToOne
    @JoinColumn(name = "user1Id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2Id")
    private User user2;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
}
