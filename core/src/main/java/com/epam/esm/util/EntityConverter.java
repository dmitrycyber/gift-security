package com.epam.esm.util;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.entity.UserEntity;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityConverter {

    public static GiftCertificateDto convertGiftEntityToDto(GiftCertificateEntity source) {
        //TODO CHECK
        Set<TagEntity> tags = source.getTagEntities();
        Set<TagDto> tagDtoSet = null;

        if (tags != null) {
            tagDtoSet = tags.stream()
                    .map(EntityConverter::convertTagEntityToDto)
                    .collect(Collectors.toSet());
        }

        Timestamp createDate = source.getCreateDate();
        Timestamp lastUpdateDate = source.getLastUpdate();

        return GiftCertificateDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .createDate(createDate == null ? null : createDate.toLocalDateTime())
                .lastUpdateDate(lastUpdateDate == null ? null : lastUpdateDate.toLocalDateTime())
                .tags(tagDtoSet)
                .build();
    }

    public static TagDto convertTagEntityToDto(TagEntity source) {
        return TagDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }

    public static GiftCertificateEntity convertGiftDtoToEntity(GiftCertificateDto source) {
        Set<TagDto> tags = source.getTags();
        Set<TagEntity> tagEntitySet = null;

        if (tags != null) {
            tagEntitySet = tags.stream()
                    .map(EntityConverter::convertTagDtoToEntity)
                    .collect(Collectors.toSet());
        }
        return GiftCertificateEntity.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .duration(source.getDuration())
                .tagEntities(tagEntitySet)
                .build();
    }

    public static TagEntity convertTagDtoToEntity(TagDto source) {
        return TagEntity.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }

    public static UserDto convertUserEntityToDto(UserEntity userEntity) {
        Set<OrderEntity> orderEntities = userEntity.getOrderEntities();

        Set<OrderDto> orderDtoSet = new HashSet<>();

        if (orderEntities != null) {
            orderDtoSet = orderEntities.stream()
                    .map(EntityConverter::convertOrderEntityToDto)
                    .collect(Collectors.toSet());
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .orders(orderDtoSet)
                .build();
    }

    public static UserEntity convertUserDtoToEntity(UserDto userDto) {
        @Valid Set<OrderDto> orderDtoSet = userDto.getOrders();

        Set<OrderEntity> orderEntities = new HashSet<>();

        if (orderDtoSet != null) {
            orderEntities = orderDtoSet.stream()
                    .map(EntityConverter::convertOrderDtoToEntity)
                    .collect(Collectors.toSet());
        }

        return UserEntity.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .orderEntities(orderEntities)
                .build();
    }

    public static OrderDto convertOrderEntityToDto(OrderEntity orderEntity) {
        if (orderEntity == null){
            return null;
        }
        Timestamp purchaseDate = orderEntity.getPurchaseDate();
        GiftCertificateEntity giftCertificateEntity = orderEntity.getGiftCertificateEntity();
        return OrderDto.builder()
                .id(orderEntity.getId())
                .cost(orderEntity.getCost())
                .giftId(giftCertificateEntity == null ? null : giftCertificateEntity.getId())
                .purchaseDate(purchaseDate == null ? null : purchaseDate.toLocalDateTime())
                .userId(orderEntity.getUserEntity().getId())
                .build();
    }

    public static OrderEntity convertOrderDtoToEntity(OrderDto orderDto) {

        return OrderEntity.builder()
                .id(orderDto.getId())
                .cost(orderDto.getCost())
                .giftCertificateEntity(GiftCertificateEntity.builder()
                        .id(orderDto.getGiftId())
                        .build())
                .userEntity(UserEntity.builder()
                        .id(orderDto.getUserId())
                        .build())
                .build();
    }
}
