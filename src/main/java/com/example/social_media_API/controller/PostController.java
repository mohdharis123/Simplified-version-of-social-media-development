package com.example.social_media_API.controller;

import com.example.social_media_API.dto.MessageResponse;
import com.example.social_media_API.exception.PostException;
import com.example.social_media_API.exception.UserException;
import com.example.social_media_API.model.Post;
import com.example.social_media_API.model.User;
import com.example.social_media_API.service.PostService;
import com.example.social_media_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestParam int id) throws UserException {

        System.out.println("create post ---- "+post.getCaption());

        User user=userService.findUserById(id);

        Post createdPost = postService.createPost(post, user.getId());

        return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
    }



    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> findPostByUserIdHandler(@PathVariable("userId") Integer userId) throws UserException{

        List<Post> posts=postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }



    @GetMapping("/following/{userIds}")
    public ResponseEntity<List<Post>> findAllPostByUserIds(@PathVariable("userIds") List<Integer> userIds) throws PostException, UserException {

        System.out.println("post userIds ----- "+userIds);
        List<Post> posts=postService.findAllPostByUserIds(userIds);

        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<Post>> findAllPostHandler() throws PostException {
        List<Post> posts=postService.findAllPost();

        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }


    // xử lý yêu cầu lấy thông tin bài viết theo Id khi có một request GET được gửi đến đường dẫn "/api/posts/{postId}".
    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException{
        Post post=postService.findPostById(postId);

        return new ResponseEntity<Post>(post,HttpStatus.OK);
    }


    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId") Integer postId, @RequestParam int id) throws UserException, PostException{

        User user=userService.findUserById(id);

        Post updatedPost=postService.likePost(postId, user.getId());

        return new ResponseEntity<Post>(updatedPost,HttpStatus.OK);

    }


    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unLikePostHandler(@PathVariable("postId") Integer postId, @RequestParam int id) throws UserException, PostException{

        User reqUser=userService.findUserById(id);

        Post updatedPost=postService.unLikePost(postId, reqUser.getId());

        return new ResponseEntity<Post>(updatedPost,HttpStatus.OK);

    }


    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<MessageResponse> deletePostHandler(@PathVariable Integer postId, @RequestParam int id) throws UserException, PostException{

        User reqUser=userService.findUserById(id);

        String message=postService.deletePost(postId, reqUser.getId());

        MessageResponse res=new MessageResponse(message);

        return new ResponseEntity<MessageResponse> (res, HttpStatus.OK);

    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<MessageResponse> savedPostHandler(@RequestParam int id,@PathVariable Integer postId) throws UserException, PostException{

        User user =userService.findUserById(id);
        String message=postService.savedPost(postId, user.getId());
        MessageResponse res=new MessageResponse(message);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<MessageResponse> unSavedPostHandler(@RequestParam int id,@PathVariable Integer postId) throws UserException, PostException{

        User user =userService.findUserById(id);
        String message=postService.unSavePost(postId, user.getId());
        MessageResponse res=new MessageResponse(message);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
