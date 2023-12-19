package com.example.social_media_API.controller;

import com.example.social_media_API.dto.MessageResponse;
import com.example.social_media_API.exception.UserException;
import com.example.social_media_API.model.User;
import com.example.social_media_API.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException {

        User user=userService.findUserById(id);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsernameHandler(@PathVariable("username") String username) throws UserException {

        User user = userService.findUserByUsername(username);

        return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);

    }

    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<MessageResponse> followUserHandler(@RequestParam int userId, @PathVariable Integer followUserId) throws UserException{
        User reqUser=userService.findUserById(userId);

        String message=userService.followUser(reqUser.getId(), followUserId);
        MessageResponse res=new MessageResponse(message);
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }

    @PutMapping("/unfollow/{unfollowUserId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@RequestParam int userId, @PathVariable Integer unfollowUserId) throws UserException{

        User reqUser=userService.findUserById(userId);

        String message=userService.unfollowUser(reqUser.getId(), unfollowUserId);
        MessageResponse res=new MessageResponse(message);
        return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
    }

//    @GetMapping("/req")
//    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException{
//
//        User user=userService.findUserProfile(token);
//
//
//        return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
//
//
//    }

    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findAllUsersByUserIdsHandler(@PathVariable List<Integer> userIds){
        List<User> users=userService.findUsersByUserIds(userIds);

        System.out.println("userIds ------ "+userIds);
        return new ResponseEntity<List<User>>(users,HttpStatus.ACCEPTED);

    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q")String query) throws UserException{

        List<User> users=userService.searchUser(query);

        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUser(@RequestParam int UserId, @RequestBody User user) throws UserException{

//        User reqUser=userService.findUserProfile(token);
        User reqUser=userService.findUserById(UserId);
        User updatedUser=userService.updateUserDetails(user, reqUser);

        return new ResponseEntity<User>(updatedUser,HttpStatus.OK);

    }
}
