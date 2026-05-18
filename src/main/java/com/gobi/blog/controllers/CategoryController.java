package com.gobi.blog.controllers;

import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.dtos.CategoryDto;
import com.gobi.blog.dtos.CreateCategoryRequest;
import com.gobi.blog.mapper.CategoryMapper;
import com.gobi.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<CategoryDto> categories = categoryService.listCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest
    ){
        Category category = categoryMapper.toEntity(createCategoryRequest);

        Category savedCategory = categoryService.createCategory(category);

        CategoryDto categoryDto = categoryMapper.toDto(savedCategory);

        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }
}
