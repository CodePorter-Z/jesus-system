package com.code.proter.user.modules.resource.service;

import com.code.proter.common.base.core.service.BaseService;
import com.code.proter.user.domain.resource.Resource;
import com.code.proter.user.modules.resource.repository.ResourceRepository;

import java.util.List;


public interface IResourceService extends BaseService<Long, Resource, ResourceRepository> {

    List<String> getResourceAll(String resourceIds);

}
