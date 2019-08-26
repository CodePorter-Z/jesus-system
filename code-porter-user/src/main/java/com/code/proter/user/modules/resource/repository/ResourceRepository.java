package com.code.proter.user.modules.resource.repository;

import com.code.proter.user.domain.resource.Resource;
import com.code.proter.common.base.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResourceRepository extends BaseRepository<Long, Resource> {

    //TODO
    //会抛出异常  java.lang.IllegalArgumentException: Encountered array-valued parameter binding, but was expecting [java.lang.Long (n/a)]
    @Query("select url from Resource where id in ( :id )")
    List<String> findUrlListById(Long[] id);

}
