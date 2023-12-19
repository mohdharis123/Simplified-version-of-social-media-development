package com.example.social_media_API.dto;

import com.example.social_media_API.Enum.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String userName;
    String emailId;
    String mobNo;
    String password;
    Gender gender;
    String bio;
    String profilePicture;
}
