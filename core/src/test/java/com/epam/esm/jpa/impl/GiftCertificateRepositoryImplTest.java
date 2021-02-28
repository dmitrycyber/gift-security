package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.criteria.GiftCriteriaBuilder;
import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@DataJpaTest
@ContextConfiguration(classes = {
        GiftCertificateRepositoryImpl.class,
        GiftCriteriaBuilder.class
})
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class GiftCertificateRepositoryImplTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void findAll() {
        List<GiftCertificateEntity> allGifts = giftCertificateRepository.findAll(1, 5);
        Assertions.assertNotNull(allGifts);
        Assertions.assertEquals(5, allGifts.size());
        allGifts
                .forEach(giftCertificateEntity -> {
                    Assertions.assertTrue(giftCertificateEntity.getName().contains("name"));
                    Assertions.assertTrue(giftCertificateEntity.getDescription().contains("description"));
                    Assertions.assertNotNull(giftCertificateEntity.getId());
                    Assertions.assertNotNull(giftCertificateEntity.getCreateDate());
                    Assertions.assertNotNull(giftCertificateEntity.getLastUpdate());
                    Assertions.assertNotNull(giftCertificateEntity.getPrice());
                    Assertions.assertNotNull(giftCertificateEntity.getDuration());
                    Assertions.assertEquals(123, giftCertificateEntity.getPrice());
                    Assertions.assertEquals(13, giftCertificateEntity.getDuration());
                });
    }

    @Test
    void findAndSortGiftsByName() {
        String namePrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .build();

        List<GiftCertificateEntity> resultList = giftCertificateRepository.findAndSortGifts(giftSearchDto, 1, 5);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(1, resultList.size());

        resultList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getName().contains(namePrefix));
        });
    }

    @Test
    public void findAndSortGiftByDescription() {
        String descriptionPrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .descriptionPrefix(descriptionPrefix)
                .build();

        List<GiftCertificateEntity> resultList = giftCertificateRepository.findAndSortGifts(giftSearchDto, 1, 5);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(1, resultList.size());

        resultList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getDescription().contains(descriptionPrefix));
        });
    }

    @Test
    public void findAndSortGiftByTagName() {
        String tagNamePrefix = "name";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .tagNamePrefixes(Collections.singletonList(tagNamePrefix))
                .build();

        List<GiftCertificateEntity> resultList = giftCertificateRepository.findAndSortGifts(giftSearchDto, 1, 5);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(5, resultList.size());

        resultList.forEach(giftCertificateEntity -> {
            giftCertificateEntity.getTagEntities().forEach(tagEntity -> {
                Assertions.assertTrue(tagEntity.getName().contains(tagNamePrefix));
            });
        });
    }

    @Test
    public void findAndSortGiftByGiftNameAndDescription() {
        String namePrefix = "1";
        String descriptionPrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .descriptionPrefix(descriptionPrefix)
                .build();

        List<GiftCertificateEntity> resultList = giftCertificateRepository.findAndSortGifts(giftSearchDto, 1, 5);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(1, resultList.size());

        resultList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getDescription().contains(descriptionPrefix));
            Assertions.assertTrue(giftCertificateEntity.getName().contains(namePrefix));
        });
    }

    @Test
    public void findAndSortGiftByGiftNameAndDescriptionAndTagName() {
        String namePrefix = "2";
        String descriptionPrefix = "2";
        String tagNamePrefix = "name3";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .descriptionPrefix(descriptionPrefix)
                .tagNamePrefixes(Collections.singletonList(tagNamePrefix))
                .build();

        List<GiftCertificateEntity> resultList = giftCertificateRepository.findAndSortGifts(giftSearchDto, 1, 5);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(1, resultList.size());
    }

    @Test
    void findById() {
        GiftCertificateEntity actualEntity = giftCertificateRepository.findById(1L);
        Assertions.assertNotNull(actualEntity.getId());
        Assertions.assertNotNull(actualEntity.getCreateDate());
        Assertions.assertNotNull(actualEntity.getLastUpdate());
        Assertions.assertNotNull(actualEntity.getPrice());
        Assertions.assertNotNull(actualEntity.getDuration());
        Assertions.assertEquals(123, actualEntity.getPrice());
        Assertions.assertEquals(13, actualEntity.getDuration());
        Assertions.assertEquals("name1", actualEntity.getName());
        Assertions.assertEquals("description1", actualEntity.getDescription());

        Set<TagEntity> tagEntities = actualEntity.getTagEntities();
        Assertions.assertNotNull(tagEntities);
        Assertions.assertEquals(2, tagEntities.size());
        tagEntities.forEach(tagEntity -> {
            Assertions.assertNotNull(tagEntity.getId());
            Assertions.assertTrue(tagEntity.getName().contains("name"));
        });
    }

    @Test
    void createGift() {
        GiftCertificateEntity entityToSave = GiftCertificateEntity.builder()
                .name("testName")
                .description("testDescription")
                .price(100)
                .duration(100)
                .build();

        GiftCertificateEntity savedEntity = giftCertificateRepository.createGift(entityToSave);
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(6, giftCertificateRepository.findAll(1, 100).size());
        Assertions.assertEquals(entityToSave.getName(), savedEntity.getName());
        Assertions.assertEquals(entityToSave.getDescription(), savedEntity.getDescription());
        Assertions.assertEquals(entityToSave.getDuration(), savedEntity.getDuration());
        Assertions.assertEquals(entityToSave.getPrice(), savedEntity.getPrice());
        Assertions.assertEquals(entityToSave.getCreateDate(), savedEntity.getCreateDate());
    }

    @Test
    void updateGift() {
        GiftCertificateEntity entityToSave = GiftCertificateEntity.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(20)
                .duration(10)
                .build();

        GiftCertificateEntity savedEntity = giftCertificateRepository.updateGift(entityToSave);
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(20, (int) giftCertificateRepository.findById(1L).getPrice());
    }

    @Test
    void deleteGift() {
        giftCertificateRepository.deleteGift(1L);

        Assertions.assertEquals(4, giftCertificateRepository.findAll(1, 100).size());
    }

    @Test
    public void giftNotFoundExceptionCheck() {
        Assertions.assertThrows(GiftNotFoundException.class, () -> giftCertificateRepository.findById(6L));
    }
}