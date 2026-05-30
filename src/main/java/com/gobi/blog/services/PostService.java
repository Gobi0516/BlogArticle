package com.gobi.blog.services;

import com.gobi.blog.domain.PostStatus;
import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.domain.entities.User;
import com.gobi.blog.dtos.CreatePostRequest;
import com.gobi.blog.dtos.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    List<Post> getAllDraftPost(User user);

    Post createPost(CreatePostRequest request, User user);
}
