package com.jesus.user.modules.role.service;

import com.jesus.common.base.core.service.BaseService;
import com.jesus.user.domain.role.Role;
import com.jesus.user.modules.role.repository.RoleRepository;

public interface IRoleService extends BaseService<Long, Role, RoleRepository> {

    Role findRoleById(Long id);
}