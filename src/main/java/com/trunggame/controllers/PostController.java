package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.PostDTO;
import com.trunggame.models.Post;
import com.trunggame.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{id}")
    public BaseResponseDTO<?> getPostById(@PathVariable Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return new BaseResponseDTO<>("Success" , 200, 200,optionalPost.get());

        } else {
            return new BaseResponseDTO<>("Content not found" , 400, 400,optionalPost.get());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createPost(@RequestBody PostDTO post) {
        Post entityPost = new Post();
        entityPost.setTitle(post.getTitle());
        entityPost.setContentEN(post.getContentEN());
        entityPost.setContentVI(post.getContentVI());
        entityPost.setImageId(post.getImageId());
        entityPost.setLink(post.getLink());
        entityPost.setAuthor(post.getAuthor());
        entityPost.setPostDate(new Date());
        Post savedPost = postRepository.save(entityPost);
        return new BaseResponseDTO<>("Success" , 200, 200,savedPost);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?>  updatePost(@PathVariable Long id, @RequestBody PostDTO post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setContentEN(post.getContentEN());
            existingPost.setContentVI(post.getContentVI());
            existingPost.setImageUrl(post.getImageUrl());
            existingPost.setLink(post.getLink());
            existingPost.setAuthor(post.getAuthor());
            postRepository.save(existingPost);
            return new BaseResponseDTO<>("Success" , 200, 200,optionalPost.get());

        } else {
            return new BaseResponseDTO<>("Fail" , 403, 403,null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?>  deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.delete(optionalPost.get());
            return new BaseResponseDTO<>("Success" , 200, 200,optionalPost.get());

        } else {
            return new BaseResponseDTO<>("Content not found" , 403, 403,null);
        }
    }

    @GetMapping
    public BaseResponseDTO<?>  getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return new BaseResponseDTO<>("Success" , 200, 200,posts);
    }
}

