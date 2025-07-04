package com.blogPost.controller;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;
import com.blogPost.services.BlogPostService.BlogPostService;
import com.blogPost.services.BlogPostService.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<BlogPost> posts = blogPostService.searchByTitleOrContent(query);
        List<User> users = userService.searchByUserName(query);

        model.addAttribute("blogposts", posts);
        model.addAttribute("users", users);
        model.addAttribute("query", query);

        return "search-results"; // This is the search result page
    }
}

