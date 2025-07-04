package com.blogPost.services.BlogPostService;


import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;
import com.blogPost.repositories.BlogpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogpostRepository blogpostRepository;

    @Autowired
    public BlogPostServiceImpl(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) {
        blogPost.setCreatedDate(new Date());
        return blogpostRepository.save(blogPost);
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogpostRepository.findAll();
    }

    @Override
    public List<BlogPost> getBlogPostUser(User user) {
        return blogpostRepository.findByAuthor(user);
    }

    @Override
    public BlogPost getBlogPostById(long blogPostId) {
        return blogpostRepository.getBlogPostById(blogPostId);
    }

    @Override
    public void deleteBlogPost(long blogPostId) {
        blogpostRepository.deleteById(blogPostId);
    }

    @Override
    public List<BlogPost> searchByTitleOrContent(String query) {
        return blogpostRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
    }



}
