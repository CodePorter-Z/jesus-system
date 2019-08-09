package com.jesus.user.modules.user.service;

import com.jesus.common.base.core.service.BaseService;
import com.jesus.user.domain.user.User;
import com.jesus.user.modules.user.repository.UserRepository;

import java.util.Set;

public interface IUserService extends BaseService<Long,User, UserRepository> {

    User findByUserName(String username);

    Set<String>  getPermissionsByUserName(String username);

}
