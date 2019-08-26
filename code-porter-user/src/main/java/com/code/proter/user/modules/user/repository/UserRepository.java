package com.code.proter.user.modules.user.repository;

import com.code.proter.user.domain.user.User;
import com.code.proter.common.base.core.repository.BaseRepository;


public interface UserRepository extends BaseRepository<Long, User> {

    User findByUsername(String username);

}
