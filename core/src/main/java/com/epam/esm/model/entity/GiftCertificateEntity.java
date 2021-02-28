package com.epam.esm.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = {
        "tagEntities",
        "orderEntities"
})
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {
        "tagEntities",
        "orderEntities"
})
@Table(name = "gift_certificates")
public class GiftCertificateEntity extends BaseEntity {

    private String name;
    private String description;
    private Integer price;
    private Integer duration;

    @ManyToMany
    @JoinTable(
            name = "gift_tags",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tagEntities;

    @OneToMany(mappedBy = "giftCertificateEntity", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.LAZY)
    private Set<OrderEntity> orderEntities;

    @Builder
    public GiftCertificateEntity(Long id, Timestamp lastUpdate, Timestamp createDate, String name, String description, Integer price, Integer duration, Set<TagEntity> tagEntities, Set<OrderEntity> orderEntities) {
        super(id, lastUpdate, createDate);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tagEntities = tagEntities;
        this.orderEntities = orderEntities;
    }
}
