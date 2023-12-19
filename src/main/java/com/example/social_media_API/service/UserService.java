package com.example.social_media_API.service;

import com.example.social_media_API.dto.UserDto;
import com.example.social_media_API.dto.UserRequestDto;
import com.example.social_media_API.dto.UserResponseDto;
import com.example.social_media_API.exception.UserException;
import com.example.social_media_API.model.User;
import com.example.social_media_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

//	@Autowired
//	private PostService postService;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

    public User registerUser(User user) throws UserException {

        System.out.println("registered user ------ ");

        Optional<User> isEmailExist = repo.findByEmailId(user.getEmailId());

        if (isEmailExist.isPresent()) {
            throw new UserException("Email Already Exist");
        }

        Optional<User> isUsernameTaken = repo.findByUsername(user.getUserName());

        if (isUsernameTaken.isPresent()) {
            throw new UserException("Username Already Taken");
        }

        if (user.getEmailId() == null || user.getPassword() == null || user.getUserName() == null) {
            throw new UserException("email,password and username are required");

        }

//        String encodedPassword=passwordEncoder.encode(user.getPassword());

        User newUser = new User();

        newUser.setEmailId(user.getEmailId());
        newUser.setPassword(user.getPassword());
        newUser.setUserName(user.getUserName());

        return repo.save(newUser);

    }


    public User findUserById(Integer userId) throws UserException {

        Optional<User> opt = repo.findById(userId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("user not found with userid :" + userId);
    }

    public User findUserByEmailId(String emailId) throws UserException {

        Optional<User> opt = repo.findByEmailId(emailId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("user not found with userid :" + emailId);
    }


    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User followUser = findUserById(followUserId);
        User reqUser = findUserById(reqUserId);

        UserDto follower = new UserDto();
        follower.setEmailId(reqUser.getEmailId());
        follower.setUserName(reqUser.getUserName());
        follower.setProfilePicture(reqUser.getProfilePicture());


        UserDto following = new UserDto();
        following.setEmailId(followUser.getEmailId());
        following.setUserName(followUser.getUserName());
        following.setProfilePicture(reqUser.getProfilePicture());


        followUser.getFollowers().add(follower);
        reqUser.getFollowings().add(following);

        repo.save(followUser);
        repo.save(reqUser);

        return "you are following " + followUser.getUserName();
    }


    public String unfollowUser(Integer reqUserId, Integer unfollowUserId) throws UserException {


        User unfollowUser = findUserById(unfollowUserId);

        System.out.println("unfollow user ---- " + unfollowUser.toString());
        System.out.println("unfollow user's follower" + unfollowUser.getFollowers().toString());

        User reqUser = findUserById(reqUserId);

        UserDto unfollow = new UserDto();
        unfollow.setEmailId(reqUser.getEmailId());
        unfollow.setUserName(reqUser.getUserName());
        unfollow.setProfilePicture(reqUser.getProfilePicture());


        UserDto following = new UserDto();
        following.setEmailId(unfollowUser.getEmailId());
        following.setUserName(unfollowUser.getUserName());
        following.setProfilePicture(unfollowUser.getProfilePicture());


        unfollowUser.getFollowers().remove(unfollow);

        repo.save(reqUser);

//		User user= userService.findUserById(userId);
//		UserDto userDto=new UserDto();
//
//		userDto.setEmail(user.getEmail());
//		userDto.setUsername(user.getUsername());
//		userDto.setId(user.getId());
//
//		Post post=findePostById(postId);
//		post.getLikedByUsers().remove(userDto);

        return "you have unfollow " + unfollowUser.getUserName();


    }

    public User findUserByUsername(String username) throws UserException {

        Optional<User> opt = repo.findByUsername(username);

        if (opt.isPresent()) {
            User user = opt.get();
            return user;
        }

        throw new UserException("user not exist with username " + username);
    }

    public List<User> findUsersByUserIds(List<Integer> userIds) {
        List<User> users = repo.findAllUserByUserIds(userIds);

        return users;
    }


    public List<User> searchUser(String query) throws UserException {
        List<User> users = repo.findByQuery(query);
        if (users.size() == 0) {
            // throw new UserException("user not exist");
        }
        return users;
    }


    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if (updatedUser.getEmailId() != null) {
            existingUser.setEmailId(updatedUser.getEmailId());
        }
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getUserName() != null) {
            existingUser.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getMobNo() != null) {
            existingUser.setMobNo(updatedUser.getMobNo());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getProfilePicture() != null) {
            existingUser.setProfilePicture(updatedUser.getProfilePicture());
        }


        // if(updatedUser.getId()==existingUser.getId()) {
        // 	System.out.println(" u "+updatedUser.getId()+" e "+existingUser.getId());
        // 	throw new UserException("you can't update another user");
        // }


        return repo.save(existingUser);
    }
}
