package com.epam.esm.jpa;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateRepository {
    /**
     * Find all gift certificates
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     * @return List GiftCertificateEntity
     * if fount no gifts - return empty list
     */
    List<GiftCertificateEntity> findAll(Integer pageNumber, Integer pageSize);

    /**
     * Find gift certificate by criteria
     *
     * @param giftSearchDto search criteria
     * @param pageNumber    pagination
     * @param pageSize      pagination
     * @return List GiftCertificateEntity which matches the search conditions
     */
    List<GiftCertificateEntity> findAndSortGifts(GiftSearchDto giftSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * Find gift certificate by gift id
     *
     * @param id gift id
     * @return GiftCertificateEntity
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if found no gifts
     */
    GiftCertificateEntity findById(Long id);

    /**
     * Create gift
     *
     * @param giftCertificateEntity entity to save
     * @return created gift
     */
    GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity);

    /**
     * Update gift
     *
     * @param giftCertificateEntity entity to update
     * @return updated gift
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if found no gifts
     */
    GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity);

    /**
     * Delete gift by id
     *
     * @param id gift id
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if found no gifts
     */
    void deleteGift(Long id);
}
