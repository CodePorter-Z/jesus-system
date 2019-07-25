package com.jesus.user.modules.user.service;

import com.jesus.user.domain.role.Role;
import com.jesus.user.domain.user.User;
import com.jesus.user.modules.permissions.repository.ResourceRepository;
import com.jesus.user.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IUserService implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
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
                .map(com.jesus.user.domain.resource.Resource::getPermission)
                .collect(Collectors.toSet());
    }
}
