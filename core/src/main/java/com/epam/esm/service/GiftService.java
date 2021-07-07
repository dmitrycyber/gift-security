package com.epam.esm.service;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;

import java.util.List;

public interface GiftService {

    /**
     * Find all gift certificates
     *
     * @param pageNumber pagination
     * @param pageSize   pagination
     * @return List GiftCertificateDto
     * if fount no gifts - return empty list
     */
    List<GiftCertificateDto> getAllGifts(Integer pageNumber, Integer pageSize);

    /**
     * Find gift certificate by criteria
     *
     * @param giftSearchDto search criteria
     * @param pageNumber    pagination
     * @param pageSize      pagination
     * @return List GiftCertificateDto which matches the search conditions
     */
    List<GiftCertificateDto> searchGifts(GiftSearchDto giftSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * Find gift certificate by gift id
     *
     * @param giftId gift id
     * @return GiftCertificateDto
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException from repository layer if found no gifts
     */
    GiftCertificateDto getGiftById(Long giftId);

    /**
     * Create gift
     *
     * @param giftCertificateDto dto to save
     * @return created gift
     */
    GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto);

    /**
     * Update gift
     *
     * @param giftCertificateDto dto to update
     *                           method support partial update
     * @return updated gift
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException from repository layer if found no gifts
     */
    GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto);

    /**
     * Delete gift by id
     *
     * @param giftId gift id
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if found no gifts
     */
    void deleteGiftById(Long giftId);
}
