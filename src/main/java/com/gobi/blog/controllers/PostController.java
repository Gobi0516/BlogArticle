package com.gobi.blog.controllers;


import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.dtos.PostDto;
import com.gobi.blog.mapper.PostMapper;
import com.gobi.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

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
}