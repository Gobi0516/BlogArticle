package com.gobi.blog.services.impl;

import com.gobi.blog.domain.entities.Tag;
import com.gobi.blog.repositories.TagRepository;
import com.gobi.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {


    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Override
    public List<Tag> createTags(Set<String> tagNames) {

        // Existing tags from database
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        // Existing tag names
        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(java.util.stream.Collectors.toSet());

        // New tags to save
        Set<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> {
                    Tag tag = new Tag();
                    tag.setName(name);
                    return tag;
                })
                .collect(Collectors.toSet());
        // Save new tags
        List<Tag> savedTags = tagRepository.saveAll(newTags);

        // Combine existing + saved tags
        existingTags.addAll(savedTags);

        return existingTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id).ifPresent(tag -> {
            if (!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Cannot delete tag with posts");
            }
            tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag findByTagId(UUID id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tag not found with id: " + id
                ));

        return tag;
    }
}
