package com.gobi.blog.mapper;

import com.gobi.blog.domain.entities.Post;
import com.gobi.blog.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PostMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "postStatus", source = "status")
    PostDto toPostDto(com.gobi.blog.domain.entities.Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
