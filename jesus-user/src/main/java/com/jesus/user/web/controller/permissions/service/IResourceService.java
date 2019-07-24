package com.jesus.user.web.controller.permissions.service;

import com.jesus.user.web.controller.permissions.repository.ResourceRepository;
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
