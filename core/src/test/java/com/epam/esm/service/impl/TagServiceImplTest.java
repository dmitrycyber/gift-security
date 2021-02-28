package com.epam.esm.service.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Integer pageNumber;
    private Integer pageSize;
    private List<TagEntity> tagEntityList;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;
        tagEntityList = new ArrayList<>();
        tagEntityList.add(TagEntity.builder()
                .id(1L)
                .name("name1")
                .build());
        tagEntityList.add(TagEntity.builder()
                .id(2L)
                .name("name2")
                .build());
        tagEntityList.add(TagEntity.builder()
                .id(3L)
                .name("name3")
                .build());
    }

    @Test
    void getAllTags() {
        Mockito.when(tagRepository.findAllTags(pageNumber, pageSize)).thenReturn(tagEntityList);
        List<TagDto> allTags = tagService.getAllTags(pageNumber, pageSize);

        Assertions.assertEquals(3, allTags.size());
        allTags
                .forEach(tagDto -> {
                    Assertions.assertNotNull(tagDto.getId());
                    Assertions.assertNotNull(tagDto.getName());
                    Assertions.assertTrue(tagDto.getName().contains("name"));
                });
    }

    @Test
    void getTagByPartName() {
        Mockito.when(tagRepository.findAllTags(Mockito.any(TagSearchDto.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(tagEntityList);
        List<TagDto> allTags = tagService.getTagByPartName(TagSearchDto.builder()
                .tagName("name")
                .build(), pageNumber, pageSize);

        Assertions.assertEquals(3, allTags.size());
        Assertions.assertTrue(allTags.get(0).getName().contains("name"));
        Assertions.assertTrue(allTags.get(0).getName().contains("name"));
        Assertions.assertTrue(allTags.get(0).getName().contains("name"));
    }

    @Test
    void getTagById() {
        long id = 1L;
        Mockito.when(tagRepository.findTagById(Mockito.anyLong())).thenReturn(TagEntity.builder()
                .id(id)
                .name("name1")
                .build());

        TagDto tagById = tagService.getTagById(id);

        Assertions.assertNotNull(tagById);
        Assertions.assertEquals("name1", tagById.getName());
    }

    @Test
    void getTagByName() {
        TagEntity tagEntity = TagEntity.builder()
                .id(1L)
                .name("name1")
                .build();

        Mockito.when(tagRepository.findTagByName(Mockito.anyString())).thenReturn(Optional.ofNullable(tagEntity));
        TagDto tagDto = tagService.getTagByName("name1");

        Assertions.assertEquals("name1", tagDto.getName());
    }

    @Test
    void createTag() {
        Mockito.when(tagRepository.createTag(Mockito.any(TagEntity.class))).thenReturn(tagEntityList.get(0));

        TagDto tagDto = tagService.createTag(TagDto.builder()
                .name("name1")
                .build());

        Assertions.assertEquals(1L, tagDto.getId());
        Assertions.assertEquals("name1", tagDto.getName());
    }

    @Test
    void deleteTagById() {
        Long id = 1L;
        tagService.deleteTagById(id);
        Mockito.verify(tagRepository, Mockito.times(1)).deleteTagById(Mockito.anyLong());
    }
}