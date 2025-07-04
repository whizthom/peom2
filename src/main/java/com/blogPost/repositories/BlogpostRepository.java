package com.blogPost.repositories;

import com.blogPost.entity.BlogPost;
import com.blogPost.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogpostRepository extends JpaRepository<BlogPost, Long> {
//    @EntityGraph(attributePaths = {"comments"})
//    @Query("SELECT b FROM BlogPost b")
    BlogPost getBlogPostById(Long id);

    List<BlogPost> findByAuthor(User user);

    List<BlogPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);


}
