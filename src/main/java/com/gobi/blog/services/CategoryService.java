package com.gobi.blog.services;

import com.gobi.blog.domain.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listCategories();

    Category createCategory(Category category);

    void deleteCategory(java.util.UUID id);
}
