package com.example.social_media_API.repository;

import com.example.social_media_API.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // Lần lượt thực hiện truy vấn
    @Query("select p from Post p where p.user.id=?1")
    public List<Post> findByUserId (Integer userId);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :users ORDER BY p.createdAt DESC")
    public List<Post> findAllPostByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :users ORDER BY p.createdAt DESC")
    public List<Post> findAllPostByUserIdsSortedByDateDesc(@Param("users") List<Integer> userIds);
}
