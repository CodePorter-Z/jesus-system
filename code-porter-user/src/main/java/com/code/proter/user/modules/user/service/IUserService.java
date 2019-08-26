package com.code.proter.user.modules.user.service;

import com.code.proter.common.base.core.service.BaseService;
import com.code.proter.user.domain.user.User;
import com.code.proter.user.modules.user.repository.UserRepository;

import java.util.Set;

public interface IUserService extends BaseService<Long,User, UserRepository> {

    User findByUserName(String username);

    Set<String>  getPermissionsByUserName(String username);

}
