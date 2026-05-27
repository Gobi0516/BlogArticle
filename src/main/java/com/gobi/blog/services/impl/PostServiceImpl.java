package com.gobi.blog.services.impl;

import com.gobi.blog.domain.PostStatus;
import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.domain.entities.Tag;
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

import java.util.List;
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
}
