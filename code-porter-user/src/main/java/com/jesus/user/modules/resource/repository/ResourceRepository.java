package com.jesus.user.modules.resource.repository;

import com.jesus.common.base.core.repository.BaseRepository;
import com.jesus.user.domain.resource.Resource;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResourceRepository extends BaseRepository<Long, Resource> {

    //TODO
    //会抛出异常  java.lang.IllegalArgumentException: Encountered array-valued parameter binding, but was expecting [java.lang.Long (n/a)]
    @Query("select url from Resource where id in ( :id )")
    List<String> findUrlListById(Long[] id);

}
