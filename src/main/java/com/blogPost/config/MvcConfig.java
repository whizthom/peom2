package com.blogPost.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = "photos";


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(UPLOAD_DIR, registry);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Be sure to use THREE forward slashes after "file:"
//        registry.addResourceHandler("/photos/**")
//                .addResourceLocations("file:///C:/Users/user/Downloads/blogPost/photos/");
//    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {

        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/" +
                uploadDir + "/**").addResourceLocations("file:" + path.toAbsolutePath() + "/" );
    }


    }



