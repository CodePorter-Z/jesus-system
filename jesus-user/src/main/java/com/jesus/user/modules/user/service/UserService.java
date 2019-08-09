package com.jesus.user.modules.user.service;

import com.jesus.user.domain.resource.Resource;
import com.jesus.user.domain.role.Role;
import com.jesus.user.domain.user.User;
import com.jesus.user.modules.resource.repository.ResourceRepository;
import com.jesus.user.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<String> getPermissionsByUserName(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null || user.getState() != User.State.ENABLED) {
            return Collections.emptySet();
        }

        Role role = user.getRole();
        if (role == null || StringUtils.isEmpty(role.getResourceIds())) {
            return Collections.emptySet();
        }

        List<Long> resourceIds = Arrays.stream(role.getResourceIds().split(",")).map(Long::parseLong)
                .collect(Collectors.toList());

        return resourceRepository.findAllById(resourceIds)
                .stream()
                .map(Resource::getPermission)
                .collect(Collectors.toSet());
    }
}
