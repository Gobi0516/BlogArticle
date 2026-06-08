package com.gobi.blog.dtos;

import com.gobi.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequestDto {

    @NotNull(message = "Post ID is required")
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 200, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 50000, message = "Content must be between {min} and {max} characters ")
    private String content;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10, message = "A post can have at most {max} tags")
    private Set<UUID> tagIds = new HashSet<>();

    @NotNull(message = "Post status is required")
    private PostStatus status;
}