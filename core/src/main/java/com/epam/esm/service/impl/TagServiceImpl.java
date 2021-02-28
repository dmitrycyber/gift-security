package com.epam.esm.service.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.exception.TagNameRegisteredException;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAllTags(Integer pageNumber, Integer pageSize) {
        List<TagEntity> allTags = tagRepository.findAllTags(pageNumber, pageSize);

        return allTags.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize) {
        List<TagEntity> tagByName = tagRepository.findAllTags(tagSearchDto, pageNumber, pageSize);

        return tagByName.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagById(Long tagId) {
        TagEntity tagById = tagRepository.findTagById(tagId);
        return EntityConverter.convertTagEntityToDto(tagById);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagByName(String tagName) {
        Optional<TagEntity> tagByName = tagRepository.findTagByName(tagName);

        return EntityConverter.convertTagEntityToDto(tagByName.orElse(null));
    }

    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) {
        Optional<TagEntity> tagByName = tagRepository.findTagByName(tagDto.getName());

        if (tagByName.isPresent()) {
            throw new TagNameRegisteredException();
        }
        TagEntity tagEntity = EntityConverter.convertTagDtoToEntity(tagDto);
        TagEntity tag = tagRepository.createTag(tagEntity);
        return EntityConverter.convertTagEntityToDto(tag);
    }

    @Override
    @Transactional
    public void deleteTagById(Long tagId) {
        tagRepository.deleteTagById(tagId);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto findMostWidelyUsedUserTag() {
        TagEntity mostWidelyUsedUserTag = tagRepository.findMostWidelyUsedUserTag();
        return EntityConverter.convertTagEntityToDto(mostWidelyUsedUserTag);
    }
}
