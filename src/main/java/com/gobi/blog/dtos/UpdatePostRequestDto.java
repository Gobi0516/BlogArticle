package com.gobi.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UpdatePostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private UUID categoryId;

    private Set<UUID> tagIds;
}