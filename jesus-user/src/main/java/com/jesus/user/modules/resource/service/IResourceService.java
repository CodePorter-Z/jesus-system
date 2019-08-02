package com.jesus.user.modules.resource.service;

import com.jesus.user.domain.resource.Resource;
import com.jesus.user.modules.resource.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IResourceService implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public ResourceRepository getRepository() {
        return resourceRepository;
    }

    @Override
    public List<Resource> getResourceAllById(String resourceIds) {

        //分割所有权限  获取id
        String[] rIds = resourceIds.split(",");
        List<Resource> resourceList = new ArrayList<>();

        //获取资源列表
        for (int i = 0; i < rIds.length; i++) {
            Long id = Long.parseLong(rIds[i]);
            resourceList.add(resourceRepository.findResourceById(id));
        }

        return resourceList;
    }
}
