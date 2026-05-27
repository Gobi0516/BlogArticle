package com.gobi.blog.repositories;

import com.gobi.blog.domain.PostStatus;
import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsByCategoryId(UUID id);

    List<Post> findAllByTagsAndCategoryAndStatus(Tag tag, Category category, PostStatus status);

    List<Post> findAllByCategoryAndStatus(Category category, PostStatus status);

    List<Post> findAllByTagsAndStatus(Tag tag, PostStatus status);

    List<Post> findAllByStatus(PostStatus status);
}
