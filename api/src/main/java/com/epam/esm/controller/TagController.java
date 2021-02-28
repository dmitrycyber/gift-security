package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Tag Controller")
public interface TagController {
    @ApiOperation(value = "Api v1. Get all tags")
    ResponseEntity<List<TagDto>> allTags(
            @ApiParam(name = "tagSearchDto", value = "Search tags by parameters")
                    TagSearchDto tagSearchDto,
            @ApiParam(name = "pageNumber", value = "pagination page number")
                    Integer pageNumber,
            @ApiParam(name = "pageNumber", value = "pagination page size")
                    Integer pageSize);

    @ApiOperation(value = "Api v1. Get tag by id")
    ResponseEntity<TagDto> tagById(Long id);

    @ApiOperation(value = "Api v1. Create tag")
    ResponseEntity<TagDto> createTag(TagDto tagDto);

    @ApiOperation(value = "Api v1. Delete tag by id")
    void deleteTag(Long id);

    @ApiOperation(value = "Api v1. find most widely user tag with the highest cost")
    TagDto findMostWidelyUsedUserTag();
}
