package com.code.proter.user.modules.role.service;

import com.code.proter.user.modules.role.repository.RoleRepository;
import com.code.proter.common.base.core.service.BaseService;
import com.code.proter.user.domain.role.Role;

public interface IRoleService extends BaseService<Long, Role, RoleRepository> {

    Role findRoleById(Long id);
}
