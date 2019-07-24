package com.jesus.user.web.controller.user.service;

import com.jesus.common.base.core.service.BaseService;
import com.jesus.user.web.controller.user.repository.UserRepository;
import com.jesus.user.model.user.User;

import java.util.Set;

public interface UserService extends BaseService<Long,User, UserRepository> {

    User findByUserName(String username);

    Set<String>  getPermissionsByUserName(String username);

}
