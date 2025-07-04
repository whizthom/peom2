package com.blogPost.services.BlogPostService.CommentService;

import com.blogPost.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment addComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteCommentById(long id);

    List<Comment> findByBlogPostId(Long blogPostId);

}
