package com.gobi.blog.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UpdatePostRequest {

    private String title;
    private String content;
    private UUID categoryId;
    private Set<UUID> tagIds;
}
