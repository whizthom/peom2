package com.blogPost.services.BlogPostService.UserService;

import com.blogPost.entity.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByUsername(String name);

    User addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    public List<User> searchByUserName(String query);
}
