package com.jesus.user.modules.resource.repository;

import com.jesus.common.base.core.repository.BaseRepository;
import com.jesus.user.domain.resource.Resource;


public interface ResourceRepository extends BaseRepository<Long, Resource> {

    Resource findResourceById(Long id);

}
