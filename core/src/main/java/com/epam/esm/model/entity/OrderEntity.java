package com.epam.esm.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.sql.Timestamp;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@SuperBuilder
public class OrderEntity extends BaseEntity {
    private Integer cost;
    private Timestamp purchaseDate;

    @ManyToOne
    @JoinColumn(name = "gift_id")
    private GiftCertificateEntity giftCertificateEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @PrePersist
    protected void onCreate() {
        super.setCreateDate(new Timestamp(System.currentTimeMillis()));
        super.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        purchaseDate = new Timestamp(System.currentTimeMillis());
    }
}
