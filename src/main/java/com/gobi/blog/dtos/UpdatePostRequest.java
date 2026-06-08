package com.gobi.blog.dtos;

import com.gobi.blog.domain.PostStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {

    private UUID id;
    private String title;
    private String content;
    private UUID categoryId;
    @Builder.Default
    private Set<UUID> tagIds = new HashSet<>();
    private PostStatus status;
}
