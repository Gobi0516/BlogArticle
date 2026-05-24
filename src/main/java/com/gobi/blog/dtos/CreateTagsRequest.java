package com.gobi.blog.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTagsRequest {
    @NotEmpty(message = "Tag names cannot be empty")
    @Size(min = 1, max = 10, message = "You can add between 1 and 10 tags")
    private Set<
            @Size(min = 2, max = 30, message = "Tag name must be between 2 and 30 characters")
            @Pattern(
                    regexp = "^[a-zA-Z0-9-_ ]+$",
                    message = "Tag name can only contain letters, numbers, spaces, hyphens, and underscores"
            )
                    String
            > names;
}
