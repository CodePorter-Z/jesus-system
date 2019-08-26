package com.code.proter.user.modules.role.service;

import com.code.proter.user.modules.role.repository.RoleRepository;
import com.code.proter.user.domain.role.Role;
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
