package com.epam.esm.jpa.impl;

import com.epam.esm.jpa.GiftCertificateJpaRepository;
import com.epam.esm.jpa.specification.CertificateSpecification;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
@ContextConfiguration(classes = {
        GiftCertificateJpaRepository.class
})
@EnableJpaRepositories("com.epam.esm.jpa")
@EntityScan(basePackages = {"com.epam.esm.model.entity"})
@ExtendWith(SpringExtension.class)
@AutoConfigurationPackage
class GiftCertificateRepositoryImplTest {
    private final Integer pageNumber = 1;
    private final Integer pageSize = 5;
    private final PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

    @Autowired
    private GiftCertificateJpaRepository giftCertificateRepository;

    @Test
    void findAll() {
        List<GiftCertificateEntity> allGifts = giftCertificateRepository.findAll(pageNumber, pageSize);
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

//    @Test
    void findAndSortGiftsByName() {
        String namePrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .build();

        Page<GiftCertificateEntity> page = giftCertificateRepository.findAll(CertificateSpecification.bySearchRequest(giftSearchDto)
                , pageRequest);

        List<GiftCertificateEntity> giftCertificateEntityList = page.get().collect(Collectors.toList());

        Assertions.assertNotNull(giftCertificateEntityList);
        Assertions.assertEquals(1, giftCertificateEntityList.size());

        giftCertificateEntityList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getName().contains(namePrefix));
        });
    }

//    @Test
    public void findAndSortGiftByDescription() {
        String descriptionPrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .descriptionPrefix(descriptionPrefix)
                .build();

        Page<GiftCertificateEntity> page = giftCertificateRepository.findAll(CertificateSpecification.bySearchRequest(giftSearchDto)
                , pageRequest);

        List<GiftCertificateEntity> giftCertificateEntityList = page.get().collect(Collectors.toList());

        Assertions.assertNotNull(giftCertificateEntityList);
        Assertions.assertEquals(1, giftCertificateEntityList.size());

        giftCertificateEntityList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getDescription().contains(descriptionPrefix));
        });
    }

    @Test
    public void findAndSortGiftByTagName() {
        String tagNamePrefix = "name1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .tagNamePrefixes(Collections.singletonList(tagNamePrefix))
                .build();

        Page<GiftCertificateEntity> page = giftCertificateRepository.findAll(CertificateSpecification.bySearchRequest(giftSearchDto)
                , pageRequest);

        List<GiftCertificateEntity> giftCertificateEntityList = page.get().collect(Collectors.toList());

        Assertions.assertNotNull(giftCertificateEntityList);
        Assertions.assertEquals(1, giftCertificateEntityList.size());

        giftCertificateEntityList.forEach(giftCertificateEntity -> {
            giftCertificateEntity.getTagEntities().forEach(tagEntity -> {
                Assertions.assertNotNull(tagEntity.getName());
            });
        });
    }

//    @Test
    public void findAndSortGiftByGiftNameAndDescription() {
        String namePrefix = "1";
        String descriptionPrefix = "1";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .descriptionPrefix(descriptionPrefix)
                .build();

        Page<GiftCertificateEntity> page = giftCertificateRepository.findAll(CertificateSpecification.bySearchRequest(giftSearchDto)
                , pageRequest);

        List<GiftCertificateEntity> giftCertificateEntityList = page.get().collect(Collectors.toList());

        Assertions.assertNotNull(giftCertificateEntityList);
        Assertions.assertEquals(1, giftCertificateEntityList.size());

        giftCertificateEntityList.forEach(giftCertificateEntity -> {
            Assertions.assertTrue(giftCertificateEntity.getDescription().contains(descriptionPrefix));
            Assertions.assertTrue(giftCertificateEntity.getName().contains(namePrefix));
        });
    }

    @Test
    public void findAndSortGiftByGiftNameAndDescriptionAndTagName() {
        String namePrefix = "name2";
        String descriptionPrefix = "description2";
        String tagNamePrefix = "name3";

        GiftSearchDto giftSearchDto = GiftSearchDto.builder()
                .namePrefix(namePrefix)
                .descriptionPrefix(descriptionPrefix)
                .tagNamePrefixes(Collections.singletonList(tagNamePrefix))
                .build();

        Page<GiftCertificateEntity> page = giftCertificateRepository.findAll(CertificateSpecification.bySearchRequest(giftSearchDto)
                , pageRequest);

        List<GiftCertificateEntity> giftCertificateEntityList = page.get().collect(Collectors.toList());

        Assertions.assertNotNull(giftCertificateEntityList);
        Assertions.assertEquals(1, giftCertificateEntityList.size());

        giftCertificateEntityList.forEach(giftCertificateEntity -> {
            Assertions.assertEquals(namePrefix, giftCertificateEntity.getName());
            Assertions.assertEquals(descriptionPrefix, giftCertificateEntity.getDescription());
            giftCertificateEntity.getTagEntities().forEach(tagEntity -> {
                Assertions.assertNotNull(tagEntity.getName());
            });
        });
    }

    @Test
    void findById() {
        GiftCertificateEntity actualEntity = giftCertificateRepository.findGiftCertificateEntityById(1L);
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

        GiftCertificateEntity savedEntity = giftCertificateRepository.save(entityToSave);
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

        GiftCertificateEntity savedEntity = giftCertificateRepository.save(entityToSave);
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(20, (int) giftCertificateRepository.findGiftCertificateEntityById(1L).getPrice());
    }

    @Test
    void deleteGift() {
        giftCertificateRepository.deleteById(1L);

        Assertions.assertEquals(4, giftCertificateRepository.findAll(1, 100).size());
    }
}