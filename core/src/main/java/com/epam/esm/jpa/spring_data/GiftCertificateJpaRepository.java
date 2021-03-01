package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateJpaRepository extends BaseRepository<GiftCertificateEntity, Long>, JpaSpecificationExecutor<GiftCertificateEntity> {

    GiftCertificateEntity findGiftCertificateEntityById(Long id);

    void deleteById(Long id);
}
