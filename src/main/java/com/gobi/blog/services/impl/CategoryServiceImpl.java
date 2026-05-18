package com.gobi.blog.services.impl;

import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.repositories.CategoryRepository;
import com.gobi.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    public Category createCategory(Category category) {
      String categoryName = category.getName().trim();
      if(categoryRepository.existsByNameIgnoreCase(categoryName)){
          throw new IllegalArgumentException("Category with name '" + categoryName + "' already exists.");
      }
        return categoryRepository.save(category);
    }

}
