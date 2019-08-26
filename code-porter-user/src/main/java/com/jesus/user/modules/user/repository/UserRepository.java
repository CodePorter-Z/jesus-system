package com.jesus.user.modules.user.repository;

import com.jesus.common.base.core.repository.BaseRepository;
import com.jesus.user.domain.user.User;


public interface UserRepository extends BaseRepository<Long,User> {

    User findByUsername(String username);

}
