package com.jesus.user.modules.role.service;

import com.jesus.user.modules.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class IRoleService implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }
}
