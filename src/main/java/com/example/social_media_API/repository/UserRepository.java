package com.example.social_media_API.repository;

import com.example.social_media_API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmailId(String emailId);

    public Optional<User> findByUsername(String userName);
    // thực hiện truy vấn vào CSDL
    @Query("SELECT u FROM User u WHERE u.id IN :users")
    public List<User> findAllUserByUserIds(@Param("users") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:query% OR u.email LIKE %:query%")
    public List<User> findByQuery(@Param("query") String query);
}
