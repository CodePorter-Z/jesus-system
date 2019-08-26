package com.jesus.user.modules.resource.service;

import com.jesus.common.base.core.service.BaseService;
import com.jesus.user.domain.resource.Resource;
import com.jesus.user.modules.resource.repository.ResourceRepository;

import java.util.List;


public interface IResourceService extends BaseService<Long, Resource, ResourceRepository> {

    List<String> getResourceAll(String resourceIds);

}
