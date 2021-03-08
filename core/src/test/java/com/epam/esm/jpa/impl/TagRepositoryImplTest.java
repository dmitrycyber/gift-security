package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateJpaRepository;
import com.epam.esm.jpa.TagJpaRepository;
import com.epam.esm.model.entity.TagEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        TagJpaRepository.class
})
@EnableJpaRepositories("com.epam.esm.jpa")
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class TagRepositoryImplTest {
    private final Integer pageNumber = 1;
    private final Integer pageSize = 5;

    @Autowired
    private TagJpaRepository tagJpaRepository;

    @Test
    void findAllTags() {
        List<TagEntity> tagEntityList = tagJpaRepository.findAll(pageNumber, pageSize);

        Assertions.assertNotNull(tagEntityList);
        Assertions.assertEquals(5, tagEntityList.size());

        tagEntityList
                .forEach(tagEntity -> {
                    Assertions.assertNotNull(tagEntity.getId());
                    Assertions.assertNotNull(tagEntity.getName());
                    Assertions.assertTrue(tagEntity.getName().contains("name"));
                });
    }

    @Test
    void findTagById() {
        long expectedId = 1L;
        String expectedName = "name1";
        TagEntity tagEntity = tagJpaRepository.findTagEntityById(expectedId);

        Assertions.assertNotNull(tagEntity);
        Assertions.assertEquals(expectedId, tagEntity.getId());
        Assertions.assertEquals(expectedName, tagEntity.getName());
    }

    @Test
    void findTagByName() {
        long expectedId = 5L;
        String expectedName = "name5";

        Optional<TagEntity> optionalTagEntity = tagJpaRepository.findByName(expectedName);

        Assertions.assertTrue(optionalTagEntity.isPresent());
        TagEntity tagEntity = optionalTagEntity.get();

        Assertions.assertEquals(expectedId, tagEntity.getId());
        Assertions.assertEquals(expectedName, tagEntity.getName());
    }

    @Test
    void createTag() {
        long expectedId = 6L;
        String expectedName = "name6";

        TagEntity tagEntityToSave = TagEntity.builder()
                .name(expectedName)
                .build();

        TagEntity savedEntity = tagJpaRepository.save(tagEntityToSave);

        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(expectedName, savedEntity.getName());
        Assertions.assertEquals(expectedId, savedEntity.getId());
    }

    @Test
    void deleteTagById() {
        long tagId = 1L;
        String tagName = "name1";

        tagJpaRepository.deleteById(tagId);

        List<TagEntity> tagEntityList = tagJpaRepository.findAll(pageNumber, pageSize);
        Assertions.assertEquals(4, tagEntityList.size());

        tagEntityList
                .forEach(tagEntity -> {
                    Assertions.assertNotEquals(tagId, tagEntity.getId());
                    Assertions.assertNotEquals(tagName, tagEntity.getName());
                });
    }
}