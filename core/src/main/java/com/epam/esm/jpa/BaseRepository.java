package com.epam.esm.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    default List<T> findAll(Integer pageNumber, Integer pageSize){
        Page<T> page = findAll(PageRequest.of(pageNumber - 1, pageSize));
        return page
                .get()
                .collect(Collectors.toList());
    }
}
