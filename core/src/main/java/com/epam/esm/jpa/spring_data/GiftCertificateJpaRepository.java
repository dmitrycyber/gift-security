package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.GiftCertificateEntity;

public interface GiftCertificateJpaRepository extends BaseRepository<GiftCertificateEntity, Long> {

    GiftCertificateEntity findGiftCertificateEntityById(Long id);

    void deleteById(Long id);
}
