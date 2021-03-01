package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagJpaRepository extends BaseRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {

    TagEntity findTagEntityById(Long id);

    Optional<TagEntity> findByName(String name);

    @Query(
            value = "SELECT t.id, t.created_date, t.last_update_date, t.name FROM orders o " +
                    "JOIN gift_certificates gc on o.gift_id = gc.id " +
                    "JOIN gift_tags gt on gc.id = gt.gift_id " +
                    "JOIN tags t on gt.tag_id = t.id " +
                    "WHERE o.user_id = " +
                    "   (SELECT user_id " +
                    "    FROM orders o " +
                    "    GROUP BY user_id " +
                    "    ORDER BY sum(o.cost) desc " +
                    "    LIMIT 1) " +
                    "GROUP BY t.id, t.created_date, t.last_update_date, t.name " +
                    "ORDER BY t.id DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
    TagEntity findMostWidelyUsedTag();
}
