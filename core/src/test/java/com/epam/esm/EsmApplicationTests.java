package com.epam.esm;

import com.epam.esm.jpa.GiftCertificateJpaRepository;
import com.epam.esm.jpa.TagJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
        TagJpaRepository.class,
        GiftCertificateJpaRepository.class
})
class EsmApplicationTests {

    @Test
    void contextLoads() {
    }
}
