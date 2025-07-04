package com.blogPost.controller;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.Comment;
import com.blogPost.entity.User;
import com.blogPost.repositories.BlogpostRepository;
import com.blogPost.repositories.CommentRepository;
import com.blogPost.repositories.UserRepository;
import com.blogPost.services.BlogPostService.BlogPostService;
import com.blogPost.services.BlogPostService.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {


    private final UserService userService;
    private final BlogPostService blogPostService;
    private final CommentRepository commentRepository;
    private final BlogpostRepository blogpostRepository;

    @Autowired
    public HomeController(UserService userService, BlogPostService blogPostService, CommentRepository commentRepository, BlogpostRepository blogpostRepository) {
        this.userService = userService;
        this.blogPostService = blogPostService;
        this.commentRepository = commentRepository;
        this.blogpostRepository = blogpostRepository;
    }

    //    @GetMapping("/")
//    public String home2(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", user);
//        return"home2";
//    }
//
//    @GetMapping("/")
//    public String home2(@Valid User user, Model model) {
//        model.addAttribute("user", user);
//        return"home2";
//    }
//
//    @GetMapping("/")
//    public String home2(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("user", user);
//        return "home2";
//    }


    @GetMapping("/")
    public String home2( Model model) {



        //Check for User authentication and then get the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            currentUser = userService.findByUsername(currentUsername);
        }


        //Add list of Blogposts
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        blogPosts.sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));



        //Add list of Users
        List<User> users = userService.getAllUsers();

        //New Comment
        Comment comment = new Comment();



        // Make sure comments are fetched for each blog post
//        for (BlogPost blogPost : blogPosts) {
//            blogPost.getComments().size(); // This will fetch the comments
//        }


        //List of comments
        List<Comment> comments = commentRepository.findAll();

        //List of comments associated to the blogPosts
//        List<Comment> byBlogPostId = commentRepository.findByBlogPostId(blogPosts.get(0).getId());


//        if (!blogPosts.isEmpty()) {
//            List<Comment> byBlogPostId = commentRepository.findByBlogPostId(blogPosts.get(0).getId());
//            // Process the comments
//        } else {
//            // Handle the case when there are no blogposts
//        }


        //All added models
//        model.addAttribute("commentsById", byBlogPostId);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", comment);
        model.addAttribute("users", users);
        model.addAttribute("blogposts", blogPosts);
        model.addAttribute("user", currentUser);
        return "home2";
    }

}
