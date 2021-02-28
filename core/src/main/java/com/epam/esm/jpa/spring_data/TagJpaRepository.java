package com.epam.esm.jpa.spring_data;

import com.epam.esm.model.entity.TagEntity;

import java.util.Optional;

public interface TagJpaRepository extends BaseRepository<TagEntity, Long> {

    TagEntity findTagEntityById(Long id);

    Optional<TagEntity> findByName(String name);
}
