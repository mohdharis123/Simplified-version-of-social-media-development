package com.example.social_media_API.service;

import com.example.social_media_API.dto.UserDto;
import com.example.social_media_API.exception.PostException;
import com.example.social_media_API.exception.UserException;
import com.example.social_media_API.model.Post;
import com.example.social_media_API.model.User;
import com.example.social_media_API.repository.PostRepository;
import com.example.social_media_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;


    public Post createPost(Post post, Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        UserDto userDto=new UserDto();

        userDto.setEmailId(user.getEmailId());
        userDto.setUserName(user.getUserName());
        userDto.setProfilePicture(user.getProfilePicture());

        post.setUser(userDto);

        // Đặt thời điểm tạo bài viết
        post.setCreatedAt(LocalDateTime.now());

        // lưu Post vào cơ sở dữ liệu
        Post createdPost =postRepo.save(post);


        return createdPost;
    }

    public List<Post> findPostByUserId(Integer userId) throws UserException {

        List<Post> posts=postRepo.findByUserId(userId);

        if(posts.size()>0) {
            return posts;
        }

        throw new UserException("This user don't have any post");
    }

    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepo.findById(postId);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new PostException("Post not exist with id: "+postId);
    }

    public List<Post> findAllPost() throws PostException {
        List<Post> posts = postRepo.findAll();
        if(posts.size()>0) {
            return posts;
        }
        throw new PostException("Post Not Exist");
    }

    public Post likePost(Integer postId, Integer userId) throws UserException, PostException  {
        // TODO Auto-generated method stub

        User user= userService.findUserById(userId);

        UserDto userDto=new UserDto();

        userDto.setEmailId(user.getEmailId());
        userDto.setUserName(user.getUserName());
        userDto.setProfilePicture(user.getProfilePicture());

        Post post=findPostById(postId);
        post.getLikedByUsers().add(userDto);


        return postRepo.save(post);


    }
    public Post unLikePost(Integer postId, Integer userId) throws UserException, PostException  {
        // TODO Auto-generated method stub

        User user= userService.findUserById(userId);
        UserDto userDto=new UserDto();

        userDto.setEmailId(user.getEmailId());
        userDto.setUserName(user.getUserName());
        userDto.setProfilePicture(user.getProfilePicture());

        Post post=findPostById(postId);
        post.getLikedByUsers().remove(userDto);



        return postRepo.save(post);
    }

    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        // TODO Auto-generated method stub

        Post post =findPostById(postId);
        User userPost=userService.findUserByEmailId(post.getUser().getEmailId());
        User user=userService.findUserById(userId);
        System.out.println(userPost.getId()+" ------ "+user.getId());
        if(userPost.getId()==(user.getId())) {
            System.out.println("inside delete");
            postRepo.deleteById(postId);

            return "Post Deleted Successfully";
        }


        throw new PostException("You Dont have access to delete this post");

    }

    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {


        List<Post> posts= postRepo.findAllPostByUserIds(userIds);

        if(posts.size()==0) {
            throw new PostException("No Post Available of your followings");
        }


        return posts;
    }

    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {

        Post post=findPostById(postId);
        User user=userService.findUserById(userId);
        if(!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepo.save(user);
        }



        return "Post Saved Successfully";
    }

    public String unSavePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post=findPostById(postId);
        User user=userService.findUserById(userId);

        if(user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepo.save(user);
        }

        return "Post Remove Successfully";
    }
}
