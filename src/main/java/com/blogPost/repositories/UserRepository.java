package com.blogPost.repositories;

import com.blogPost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    List<User> findByUserNameContainingIgnoreCase(String userName);

}
