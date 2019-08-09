package com.jesus.user.modules.role.service;

import com.jesus.user.domain.role.Role;
import com.jesus.user.modules.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RoleService implements IRoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return roleRepository;
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findRoleById(id);
    }
}
