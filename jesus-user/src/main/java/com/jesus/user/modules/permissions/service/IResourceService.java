package com.jesus.user.modules.permissions.service;

import com.jesus.user.modules.permissions.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class IResourceService implements ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public ResourceRepository getRepository() {
        return resourceRepository;
    }
}
