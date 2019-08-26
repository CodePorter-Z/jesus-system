package com.code.proter.common.base.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<ID extends Number, T extends Serializable> extends JpaRepository<T, ID> {

    Page<T> findAll(Specification<T> spec, Pageable pageable);
}