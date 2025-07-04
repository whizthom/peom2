package com.blogPost.controller;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;
import com.blogPost.services.BlogPostService.BlogPostService;
import com.blogPost.services.BlogPostService.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/blogpost")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final UserService userService;

    @Autowired
    public BlogPostController(BlogPostService blogPostService, UserService userService) {
        this.blogPostService = blogPostService;
        this.userService = userService;
    }

    @GetMapping("/form")
    public String blogpostForm(Model model) {
        model.addAttribute("blogpost", new BlogPost());
        return "blogpost-form";
    }

    @PostMapping("/add")
    public String addBlogPost(@Valid BlogPost blogPost, Model model) {
        List<BlogPost> allBlogpost = blogPostService.getAllBlogPosts();
        model.addAttribute("blogposts", allBlogpost);

        //check for User Authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUsername = auth.getName();
            currentUser = userService.findByUsername(currentUsername);
        }
        model.addAttribute("user", currentUser);

        blogPost.setAuthor(currentUser);
        blogPostService.createBlogPost(blogPost);


//        User user = userService.findByUsername(principal.getName());

//        blogPost.setAuthor(userService.findById((long) user.getId()));
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String editBlogPostForm(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable Long id,
                                   Model model) {

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        // I got the particular
        BlogPost blogpost = blogPostService.getBlogPostById(id);

        model.addAttribute("blogpost", blogpost);
        model.addAttribute("user", user);

        return "edit-blogpost-form";
    }

    @PostMapping("/edit")
    public String editBlogPost(@AuthenticationPrincipal UserDetails userDetails,
            @Valid BlogPost blogPost, Model model) {

        User user = userService.findByUsername(userDetails.getUsername());

        blogPost.setAuthor(user);
        blogPost.setUpdatedDate(new Date());
        blogPostService.createBlogPost(blogPost);
        return "redirect:/user/profile";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable Long id, Model model) {

        blogPostService.deleteBlogPost(id);

        return "redirect:/user/profile";
    }
}
