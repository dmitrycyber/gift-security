package com.epam.esm.jpa;

import com.epam.esm.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagJpaRepository extends BaseRepository<TagEntity, Long>, JpaSpecificationExecutor<TagEntity> {

    /**
     * Find tags by tag id
     *
     * @param id tag id
     * @return TagEntity
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if found no tags
     */
    TagEntity findTagEntityById(Long id);

    /**
     * Find tags by full tag name
     *
     * @param name tag name
     * @return List TagEntity which matches the search conditions
     */
    Optional<TagEntity> findByName(String name);

    /**
     * Find most widely used user`s tag with highest cost of all orders
     *
     * @return most widely user tag with the highest cost
     */
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
