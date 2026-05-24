package com.gobi.blog.controllers;

import com.gobi.blog.domain.entities.Tag;
import com.gobi.blog.dtos.CreateTagsRequest;
import com.gobi.blog.dtos.TagResponse;
import com.gobi.blog.mapper.TagMapper;
import com.gobi.blog.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@Valid @RequestBody CreateTagsRequest request) {
        List<Tag> tags = tagService.createTags(request.getNames());
        List<TagResponse> responses = tags.stream()
                .map(tagMapper::toTagResponse)
                .toList();

        return new ResponseEntity<>(
                responses,
                HttpStatus.CREATED
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
