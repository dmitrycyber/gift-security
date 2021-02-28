package com.epam.esm.controller;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Gift Controller")
public interface GiftController {

    @ApiOperation(value = "Api v1. Get all gifts")
    ResponseEntity<List<GiftCertificateDto>> allGifts(
            @ApiParam(name = "customSearchRequestDto", value = "Search and sort gift certificates by parameters")
                    GiftSearchDto giftSearchDto,
            @ApiParam(name = "pageNumber", value = "pagination page number")
                    Integer pageNumber,
            @ApiParam(name = "pageNumber", value = "pagination page size")
                    Integer pageSize);

    @ApiOperation(value = "Api v1. Get gift by id")
    ResponseEntity<GiftCertificateDto> giftById(Long id);

    @ApiOperation(value = "Api v1. Create gift")
    ResponseEntity<GiftCertificateDto> createGift(GiftCertificateDto giftCertificateDto);

    @ApiOperation(value = "Api v1. Update gift")
    ResponseEntity<GiftCertificateDto> updateGift(Long id, GiftCertificateDto giftCertificateDto);

    @ApiOperation(value = "Api v1. Delete gift by id")
    void deleteGift(Long id);
}
