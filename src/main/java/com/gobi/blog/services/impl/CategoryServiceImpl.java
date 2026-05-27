package com.gobi.blog.services.impl;

import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.domain.entities.Tag;
import com.gobi.blog.repositories.CategoryRepository;
import com.gobi.blog.repositories.PostRepository;
import com.gobi.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    public Category createCategory(Category category) {

        String categoryName = category.getName().trim();

        if (categoryRepository.existsByNameIgnoreCase(categoryName)) {
            throw new IllegalArgumentException("Category with name '" + categoryName + "' already exists.");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));

        // Business rule: prevent deletion if posts exist
        boolean hasPosts = postRepository.existsByCategoryId(id);
        if (hasPosts) {
            throw new IllegalStateException("Cannot delete category. Posts are associated with it.");
        }
        categoryRepository.delete(category);
    }


    @Override
    public Category findByCategoryId(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }
}
