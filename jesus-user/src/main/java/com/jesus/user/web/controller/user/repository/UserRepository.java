package com.jesus.user.web.controller.user.repository;

import com.jesus.common.base.core.repository.BaseRepository;
import com.jesus.user.model.user.User;


public interface UserRepository extends BaseRepository<Long,User> {

    User findByUsername(String username);

}
