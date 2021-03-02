package com.epam.esm.service.impl;

import com.epam.esm.jpa.exception.TagNameRegisteredException;
import com.epam.esm.jpa.TagJpaRepository;
import com.epam.esm.jpa.specification.TagSpecification;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagJpaRepository tagJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getAllTags(Integer pageNumber, Integer pageSize) {
        Page<TagEntity> tagEntityPage = tagJpaRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));

        List<TagEntity> allTags = tagEntityPage.get()
                .collect(Collectors.toList());

        return allTags.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> getTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize) {
        Page<TagEntity> tagEntityPage = tagJpaRepository.findAll(TagSpecification.bySearchDto(tagSearchDto), PageRequest.of(pageNumber - 1, pageSize));

        List<TagEntity> tagByName = tagEntityPage.get().collect(Collectors.toList());

        return tagByName.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagById(Long tagId) {
        TagEntity tagById = tagJpaRepository.findTagEntityById(tagId);
        return EntityConverter.convertTagEntityToDto(tagById);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto getTagByName(String tagName) {
        Optional<TagEntity> tagByName = tagJpaRepository.findByName(tagName);

        return EntityConverter.convertTagEntityToDto(tagByName.orElse(null));
    }

    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) {
        Optional<TagEntity> tagByName = tagJpaRepository.findByName(tagDto.getName());

        if (tagByName.isPresent()) {
            throw new TagNameRegisteredException();
        }
        TagEntity tagEntity = EntityConverter.convertTagDtoToEntity(tagDto);
        TagEntity tag = tagJpaRepository.save(tagEntity);
        return EntityConverter.convertTagEntityToDto(tag);
    }

    @Override
    @Transactional
    public void deleteTagById(Long tagId) {
        tagJpaRepository.deleteById(tagId);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDto findMostWidelyUsedUserTag() {
        TagEntity mostWidelyUsedUserTag = tagJpaRepository.findMostWidelyUsedTag();
        return EntityConverter.convertTagEntityToDto(mostWidelyUsedUserTag);
    }
}
