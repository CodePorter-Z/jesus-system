package com.jesus.user.logger.service;

import com.jesus.user.logger.repository.LogActionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ILogActionService implements LogActionService {

    @Resource
    private LogActionRepository logActionRepository;

    @Override
    public LogActionRepository getRepository() {
        return logActionRepository;
    }
}
