package com.blogPost.services.BlogPostService.CommentService;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.Comment;
import com.blogPost.entity.User;
import com.blogPost.repositories.CommentRepository;
import com.blogPost.services.BlogPostService.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }


    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment addComment(Comment comment) {
//        comment.setCreatedDate(new Date());
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User username = null;
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            String currentUser = auth.getName();
//            username = userService.findByUsername(currentUser);
//        }
//        comment.setAuthor(username);
//        comment.setBlogPost(comment.getBlogPost());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Comment foundComment = commentRepository.findById(comment.getId()).orElse(null);
        if (foundComment != null) {
            throw new RuntimeException("Comment not found");
        }
        return commentRepository.save(foundComment);
    }

    @Override
    public void deleteCommentById(long Id) {
        commentRepository.deleteById(Id);
    }

    @Override
    public List<Comment> findByBlogPostId(Long blogPostId) {
        return commentRepository.findByBlogPostId(blogPostId);
    }
}
