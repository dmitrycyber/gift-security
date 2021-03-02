package com.epam.esm.controller.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
@RequiredArgsConstructor
@Slf4j
public class GiftControllerImpl implements GiftController {
    private final GiftService giftService;
    private static final GiftSearchDto defaultCustomSearchRequest = GiftSearchDto.builder()
            .build();

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public ResponseEntity<List<GiftCertificateDto>> allGifts(
            @Valid GiftSearchDto giftSearchDto,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        List<GiftCertificateDto> allGifts = !defaultCustomSearchRequest.equals(giftSearchDto)
                ? giftService.searchGifts(giftSearchDto, pageNumber, pageSize)
                : giftService.getAllGifts(pageNumber, pageSize);

        allGifts.forEach(this::addSelfLinks);
        return ResponseEntity.ok(allGifts);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public ResponseEntity<GiftCertificateDto> giftById(@PathVariable Long id) {
        GiftCertificateDto giftById = giftService.getGiftById(id);

        addSelfLinks(giftById);
        return ResponseEntity.ok(giftById);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public ResponseEntity<GiftCertificateDto> createGift(
            @RequestBody @Valid @Validated(CreatingDto.class) GiftCertificateDto giftCertificateDto) {
        log.info("DTO TO SAVE " + giftCertificateDto);

        GiftCertificateDto createdGift = giftService.createGift(giftCertificateDto);

        addSelfLinks(createdGift);
        return ResponseEntity.ok(createdGift);
    }

    @Override
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public ResponseEntity<GiftCertificateDto> updateGift(
            @PathVariable Long id,
            @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);

        GiftCertificateDto updatedGift = giftService.updateGift(giftCertificateDto);

        addSelfLinks(updatedGift);
        return ResponseEntity.ok(updatedGift);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority(T(com.epam.esm.util.UserType).ROLE_USER.name())" +
            " or hasAuthority(T(com.epam.esm.util.UserType).ROLE_ADMIN.name())")
    public void deleteGift(
            @PathVariable Long id
    ) {
        giftService.deleteGiftById(id);
    }

    private void addSelfLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.getTags()
                .forEach(tagDto -> {
                    tagDto.add(WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder
                                    .methodOn(TagControllerImpl.class)
                                    .tagById(tagDto.getId()))
                            .withSelfRel());
                });
        giftCertificateDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(GiftControllerImpl.class)
                        .giftById(giftCertificateDto.getId()))
                .withSelfRel());
    }
}
