package com.example.social_media_API.dto;

import com.example.social_media_API.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponseDto {

    String userName;
    String emailId;
    String mobNo;
    Gender gender;
    String bio;
    String profilePicture;
}
