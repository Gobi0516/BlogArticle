package com.gobi.blog.controllers;


import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.domain.entities.User;
import com.gobi.blog.dtos.*;
import com.gobi.blog.mapper.PostMapper;
import com.gobi.blog.services.PostService;
import com.gobi.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId
    ) {

        List<Post> posts = postService.getAllPosts(categoryId, tagId);

        List<PostDto> responses = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getAllDraftPosts(@RequestAttribute UUID userId) {
        User user = userService.getUserById(userId);
        List<Post> posts = postService.getAllDraftPost(user);

        List<PostDto> responses = posts.stream()
                .map(postMapper::toPostDto)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId
    ) {
        User user = userService.getUserById(userId);
        CreatePostRequest request = postMapper.toCreatePostRequest(createPostRequestDto);
        Post post = postService.createPost(request, user);
        PostDto postDto = postMapper.toPostDto(post);

        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID postId,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto
    ) {

        UpdatePostRequest request = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(postId, request);
        PostDto postDto = postMapper.toPostDto(updatedPost);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID postId) {
        Post post = postService.getPostById(postId);
        PostDto postDto = postMapper.toPostDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}