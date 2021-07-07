package com.epam.esm.service.impl;

import com.epam.esm.jpa.TagJpaRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagJpaRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Integer pageNumber;
    private Integer pageSize;
    private List<TagEntity> tagEntityList;
    private PageRequest pageRequest;
    private Page page;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;
        pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        tagEntityList = LongStream.range(1, 4)
                .mapToObj(index -> TagEntity.builder()
                        .id(index)
                        .name("name" + index)
                        .build())
                .collect(Collectors.toList());

        page = new PageImpl<>(tagEntityList);
    }

    @Test
    void getAllTags() {
        Mockito.when(tagRepository.findAll(pageNumber, pageSize)).thenReturn(tagEntityList);
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
        Mockito
                .when(tagRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

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
        Mockito.when(tagRepository.findTagEntityById(Mockito.anyLong())).thenReturn(tagEntityList.get(0));

        TagDto tagById = tagService.getTagById(id);

        Assertions.assertNotNull(tagById);
        Assertions.assertEquals("name1", tagById.getName());
    }

    @Test
    void getTagByName() {
        String expectedName = "name1";

        Mockito.when(tagRepository.findByName(expectedName)).thenReturn(Optional.ofNullable(tagEntityList.get(0)));
        TagDto tagDto = tagService.getTagByName("name1");

        Assertions.assertEquals("name1", tagDto.getName());
    }

    @Test
    void createTag() {
        Mockito.when(tagRepository.save(Mockito.any(TagEntity.class))).thenReturn(tagEntityList.get(0));

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
        Mockito.verify(tagRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}