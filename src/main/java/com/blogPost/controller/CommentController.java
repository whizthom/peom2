package com.blogPost.controller;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.Comment;
import com.blogPost.entity.User;
import com.blogPost.services.BlogPostService.BlogPostService;
import com.blogPost.services.BlogPostService.CommentService.CommentService;
import com.blogPost.services.BlogPostService.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final BlogPostService blogPostService;

    public CommentController(CommentService commentService, UserService userService, BlogPostService blogPostService) {
        this.commentService = commentService;
        this.userService = userService;
        this.blogPostService = blogPostService;
    }


    @PostMapping("/create/{blogPostId}")
    public String createComment(@PathVariable long blogPostId
            ,@Valid Comment comment, Model model) {




        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User username = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUsername = auth.getName();
            username = userService.findByUsername(currentUsername);
        }


        comment.setAuthor(username);
        comment.setCreatedDate(new Date());

        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);
        comment.setBlogPost(blogPost);


        commentService.addComment(comment);

        return "redirect:/";
    }
}
