package com.epam.esm.config;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.order.OrderDto;
import com.epam.esm.model.dto.user.UserDto;
import com.epam.esm.service.GiftService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Profile("db")
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {
    private final OrderService orderService;
    private final UserService userService;
    private final GiftService giftService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createUsers();
        createGiftsWithTags();
        createOrders();
    }

    private void createOrders() {
        LongStream.range(1, 1001)
                .forEach(index -> {
                    OrderDto orderDto = OrderDto.builder()
                            .giftId(ThreadLocalRandom.current().nextLong(2004, 2599))
                            .userId(ThreadLocalRandom.current().nextLong(102, 199))
                            .build();
                    orderService.createOrder(orderDto);
                });
    }

    private void createGiftsWithTags() {
        List<TagDto> tags = LongStream.range(1, 1001)
                .mapToObj(index -> TagDto.builder()
                        .name("tagName" + index)
                        .build())
                .collect(Collectors.toList());

        LongStream.range(1, 1001)
                .forEach(index -> {
                    GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                            .name("gift name" + index)
                            .description("gift description " + index)
                            .price((int) index)
                            .duration((int) index)
                            .tags(getRandomTags(tags))
                            .build();
                    giftService.createGift(giftCertificateDto);
                });
    }

    private Set<TagDto> getRandomTags(List<TagDto> tags) {
        return IntStream.range(1, 6)
                .mapToObj(index -> tags.get(ThreadLocalRandom.current().nextInt(1, 1000)))
                .collect(Collectors.toSet());
    }

    private void createUsers() {
        LongStream.range(1, 100)
                .forEach(index -> {
                    userService.register(UserDto.builder()
                            .firstName("Name " + index)
                            .lastName("Surname " + index)
                            .phoneNumber("+37529" + index)
                            .password("password" + index)
                            .email("mail" + index + "@mail.com")
                            .build());
                });
    }
}
