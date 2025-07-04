package com.blogPost.controller;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;
import com.blogPost.services.BlogPostService.BlogPostService;
import com.blogPost.services.BlogPostService.UserService.UserService;
import com.blogPost.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BlogPostService blogPostService;

    @Autowired
    public UserController(UserService userService, BlogPostService blogPostService) {
        this.userService = userService;
        this.blogPostService = blogPostService;
    }


    @GetMapping("/signup")
    public String signUp(Model model) {
        User user = new User();

        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user , Model model) {

        User username = userService.findByUsername(user.getUserName());
        if(username != null) {
            model.addAttribute("error", "Username is already taken. Try another one.");
            List<User> getUsers = userService.getAllUsers();

            model.addAttribute("user", user);
            model.addAttribute("users", getUsers);
            return "signup";
        }else{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.addUser(user);
            return "redirect:/user/login";
        }
    }

    @GetMapping("/login")
    public String login(@Valid User user, Model model){
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/update-form")
    public String updateForm(@AuthenticationPrincipal UserDetails userDetails, Model model){

        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "update-form";
    }

    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam("image") MultipartFile profilePicture){

        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        String fileName = "";
        if(!(profilePicture.getOriginalFilename().equals(""))){
            fileName = StringUtils.cleanPath(Objects.requireNonNull(
                    profilePicture.getOriginalFilename()
            ));
            user.setProfilePictureName(fileName);

        }



        user.setProfilePictureName(fileName);
        userService.addUser(user);
        String uploadDir = "photos/"+user.getUserName();

        try{
            FileUploadUtil.saveFile(uploadDir,fileName,profilePicture);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    return "redirect:/";
    }


    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model){

        User user = userService.findByUsername(userDetails.getUsername());

        //I Added list of Blogposts specifically for the user only.
        List<BlogPost> blogposts = blogPostService.getBlogPostUser(user);
        blogposts.sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));

        model.addAttribute("user", user);
        model.addAttribute("blogposts", blogposts);

        return "profile";
    }

    @GetMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.findByUsername(userDetails.getUsername());
        userService.deleteUser((long) user.getId());
        return "redirect:/user/login";
    }

    @GetMapping("/profile/{username}")
    public String generalProfileView(@PathVariable String username, Model model){
        User user = userService.findByUsername(username);
        List<BlogPost> blogposts = blogPostService.getBlogPostUser(user);

        model.addAttribute("user", user);
        model.addAttribute("blogposts", blogposts);

        return"general-profile-view";
    }

}
