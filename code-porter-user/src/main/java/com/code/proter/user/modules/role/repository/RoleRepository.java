package com.code.proter.user.modules.role.repository;

import com.code.proter.common.base.core.repository.BaseRepository;
import com.code.proter.user.domain.role.Role;

public interface RoleRepository extends BaseRepository<Long, Role> {

    Role findRoleById(Long id);

}
