package com.epam.esm.jpa;

import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateJpaRepository extends BaseRepository<GiftCertificateEntity, Long>, JpaSpecificationExecutor<GiftCertificateEntity> {

    /**
     * Find gift certificate by gift id
     *
     * @param id gift id
     * @return GiftCertificateEntity
     */
    GiftCertificateEntity findGiftCertificateEntityById(Long id);

    /**
     * Delete gift by id
     *
     * @param id gift id
     */
    void deleteById(Long id);
}
