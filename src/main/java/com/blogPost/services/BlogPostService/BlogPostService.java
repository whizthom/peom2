package com.blogPost.services.BlogPostService;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;

import java.util.List;
import java.util.Optional;

public interface BlogPostService {

    BlogPost createBlogPost(BlogPost blogPost);

    List<BlogPost> getAllBlogPosts();

    List<BlogPost> getBlogPostUser(User user);

    BlogPost getBlogPostById(long blogPostId);

    void deleteBlogPost(long blogPostId);

    List<BlogPost> searchByTitleOrContent(String query);
}
