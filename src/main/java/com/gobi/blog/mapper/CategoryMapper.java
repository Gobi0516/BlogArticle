package com.gobi.blog.mapper;

import com.gobi.blog.domain.PostStatus;
import com.gobi.blog.domain.entities.Category;
import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.dtos.CategoryDto;
import com.gobi.blog.dtos.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target="postCount",source="posts" ,qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);


    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePublishedPostCount(List<Post> posts) {

        if (posts == null) {
            return 0;
        }

        return  posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
    
}
