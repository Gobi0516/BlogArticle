package com.gobi.blog.services.impl;

import com.gobi.blog.domain.PostStatus;
import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.domain.entities.Tag;
import com.gobi.blog.domain.entities.User;
import com.gobi.blog.dtos.CreatePostRequest;
import com.gobi.blog.dtos.PostDto;
import com.gobi.blog.repositories.CategoryRepository;
import com.gobi.blog.repositories.PostRepository;
import com.gobi.blog.repositories.TagRepository;
import com.gobi.blog.services.CategoryService;
import com.gobi.blog.services.PostService;
import com.gobi.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;


    @Override
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        // validate category
        if (categoryId != null) {
            Category category = categoryService.findByCategoryId(categoryId);
            return postRepository.findAllByCategoryAndStatus(category, PostStatus.PUBLISHED);

        }

        // validate tag
        if (tagId != null) {
            Tag tag = tagService.findByTagId(tagId);
            return postRepository.findAllByTagsAndStatus(tag, PostStatus.PUBLISHED);
        }

        // both available
        if (categoryId != null && tagId != null) {
            Category category = categoryService.findByCategoryId(categoryId);
            Tag tag = tagService.findByTagId(tagId);
            return postRepository
                    .findAllByTagsAndCategoryAndStatus(
                            tag,
                            category,
                            PostStatus.PUBLISHED
                    );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);

    }


    @Override
    public Post createPost(CreatePostRequest request, User user) {

        Category category = categoryService.findByCategoryId(request.getCategoryId());

        Set<Tag> tags = new HashSet<>();

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {

            request.getTagIds().forEach(tagId -> {
                Tag tag = tagService.findByTagId(tagId);
                tags.add(tag);
            });
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .tags(tags)
                .author(user)
                .status(request.getStatus())
                .readingTime(calculateReadingTime(request.getContent()))
                .build();

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllDraftPost(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);

    }

    private Integer calculateReadingTime(String content) {
        int words = content.trim().split("\\s+").length;
        return Math.max(1, (int) Math.ceil(words / 200.0));
    }
}
