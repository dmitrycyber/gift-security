package com.epam.esm.jpa;

import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    /**
     * Find all tags
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     *                   if fount no tags - return empty list
     * @return List TagEntity
     */
    List<TagEntity> findAllTags(Integer pageNumber, Integer pageSize);

    /**
     * Find tags by tag id
     *
     * @param tagId tag id
     * @return TagEntity
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if found no tags
     */
    TagEntity findTagById(Long tagId);

    /**
     * Find tags by full tag name
     *
     * @param tagName tag name
     * @return List TagEntity which matches the search conditions
     */
    Optional<TagEntity> findTagByName(String tagName);

    /**
     * Find tags by full part tag name
     *
     * @param tagSearchDto search criteria
     * @param pageNumber   pagination
     * @param pageSize     pagination
     * @return List TagEntity which matches the search conditions
     */
    List<TagEntity> findAllTags(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * Create tag
     *
     * @param tagEntity entity to save
     * @return created tag
     */
    TagEntity createTag(TagEntity tagEntity);

    /**
     * Delete tag by id
     *
     * @param tagId tag id
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if found no tags
     */
    void deleteTagById(Long tagId);

    /**
     * Find most widely used user`s tag with highest cost of all orders
     *
     * @return most widely user tag with the highest cost
     */
    TagEntity findMostWidelyUsedUserTag();
}
