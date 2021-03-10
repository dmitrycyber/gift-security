package com.epam.esm.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = {
        "giftCertificateEntities"
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
@SuperBuilder
public class TagEntity extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "tagEntities")
    private Set<GiftCertificateEntity> giftCertificateEntities;

    @PreRemove
    private void removeTagsFromGifts() {
        for (GiftCertificateEntity giftCertificateEntity : giftCertificateEntities) {
            giftCertificateEntity.getTagEntities().remove(this);
        }
    }
}
