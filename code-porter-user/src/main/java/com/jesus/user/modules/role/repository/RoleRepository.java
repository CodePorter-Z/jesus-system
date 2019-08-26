package com.jesus.user.modules.role.repository;

import com.jesus.common.base.core.repository.BaseRepository;
import com.jesus.user.domain.role.Role;

public interface RoleRepository extends BaseRepository<Long, Role> {

    Role findRoleById(Long id);

}
