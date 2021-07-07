package com.epam.esm.service;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;

import java.util.List;

public interface TagService {

    /**
     * Find all tags
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     *                   if fount no tags - return empty list
     * @return List TagDto
     */
    List<TagDto> getAllTags(Integer pageNumber, Integer pageSize);

    /**
     * Find tags by tag id
     *
     * @param tagId tag id
     * @return TagDto
     * @throws com.epam.esm.jpa.exception.TagNotFoundException from repository layer if found no tags
     */
    TagDto getTagById(Long tagId);

    /**
     * Find tags by full tag name
     *
     * @param tagName tag name
     * @return List TagDto which matches the search conditions
     */
    TagDto getTagByName(String tagName);

    /**
     * Find tags by full part tag name
     *
     * @param tagSearchDto search criteria
     * @param pageNumber   pagination
     * @param pageSize     pagination
     * @return List TagDto which matches the search conditions
     */
    List<TagDto> getTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * Create tag
     *
     * @param tagDto entity to save
     * @return created tag
     */
    TagDto createTag(TagDto tagDto);

    /**
     * Delete tag by id
     *
     * @param tagId tag id
     * @throws com.epam.esm.jpa.exception.TagNotFoundException from repository layer if found no tags
     */
    void deleteTagById(Long tagId);

    /**
     * Find most widely used user tag
     *
     * @return most widely user tag with the highest cost
     */
    TagDto findMostWidelyUsedUserTag();
}
