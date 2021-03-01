package com.epam.esm.service.impl;

import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.jpa.spring_data.specification.CertificateSpecification;
import com.epam.esm.jpa.spring_data.GiftCertificateJpaRepository;
import com.epam.esm.jpa.spring_data.TagJpaRepository;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.service.GiftService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftServiceImpl implements GiftService {
    private final GiftCertificateJpaRepository giftCertificateJpaRepository;
    private final TagJpaRepository tagJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDto> getAllGifts(Integer pageNumber, Integer pageSize) {
        Page<GiftCertificateEntity> giftCertificateEntityPage = giftCertificateJpaRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));

        List<GiftCertificateEntity> giftCertificateEntityList = giftCertificateEntityPage
                .get()
                .collect(Collectors.toList());


        return giftCertificateEntityList.stream()
                .map(EntityConverter::convertGiftEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDto> searchGifts(GiftSearchDto customSearchRequest, Integer pageNumber, Integer pageSize) {
        Page<GiftCertificateEntity> page = giftCertificateJpaRepository.findAll(
                CertificateSpecification.bySearchRequest(customSearchRequest)
                , PageRequest.of(pageNumber - 1, pageSize));

        List<GiftCertificateEntity> giftCertificateEntityList = page.get()
                .collect(Collectors.toList());

        return giftCertificateEntityList.stream()
                .map(EntityConverter::convertGiftEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDto getGiftById(Long giftId) {
        GiftCertificateEntity giftById = giftCertificateJpaRepository.findGiftCertificateEntityById(giftId);
        return EntityConverter.convertGiftEntityToDto(giftById);
    }

    @Override
    @Transactional
    public GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto) {
        GiftCertificateEntity giftEntityToSave = EntityConverter.convertGiftDtoToEntity(giftCertificateDto);

        Set<TagEntity> tagsToSave = giftEntityToSave.getTagEntities();
        Set<TagEntity> savedTags = createTagsIfNeeded(tagsToSave);

        giftEntityToSave.setTagEntities(savedTags);

        GiftCertificateEntity savedEntity = giftCertificateJpaRepository.save(giftEntityToSave);

        return EntityConverter.convertGiftEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto) {
        GiftCertificateEntity giftEntityToSave = giftCertificateJpaRepository.findGiftCertificateEntityById(giftCertificateDto.getId());

        checkIfGiftNotFound(giftEntityToSave);

        if (giftCertificateDto.getName() != null) {
            giftEntityToSave.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            giftEntityToSave.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            giftEntityToSave.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            giftEntityToSave.setDuration(giftCertificateDto.getDuration());
        }

        Set<TagEntity> tagsToSave = giftCertificateDto.getTags().stream()
                .map(EntityConverter::convertTagDtoToEntity)
                .collect(Collectors.toSet());

        Set<TagEntity> savedTags = createTagsIfNeeded(tagsToSave);
        giftEntityToSave.setTagEntities(savedTags);

        return EntityConverter.convertGiftEntityToDto(giftEntityToSave);
    }

    @Override
    @Transactional
    public void deleteGiftById(Long giftId) {
        GiftCertificateEntity giftCertificateEntity = giftCertificateJpaRepository.findGiftCertificateEntityById(giftId);

        Set<OrderEntity> orderEntities = giftCertificateEntity.getOrderEntities();

        orderEntities.forEach(orderEntity -> orderEntity.setGiftCertificateEntity(null));

        giftCertificateJpaRepository.deleteById(giftId);
    }

    private Set<TagEntity> createTagsIfNeeded(Set<TagEntity> tagEntities) {
        Set<TagEntity> savedTags = new HashSet<>();
        tagEntities
                .forEach(tagEntity -> {
                    Optional<TagEntity> tagByName = tagJpaRepository.findByName(tagEntity.getName());
                    if (tagByName.isEmpty()) {
                        tagJpaRepository.save(tagEntity);
                        savedTags.add(tagEntity);
                    } else {
                        savedTags.add(tagByName.get());
                    }
                });
        return savedTags;
    }

    private void checkIfGiftNotFound(GiftCertificateEntity giftById) {
        if (giftById == null) {
            throw new GiftNotFoundException();
        }
    }
}
