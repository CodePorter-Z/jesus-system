package com.code.proter.user.logger.service;

import com.code.proter.user.logger.repository.LogActionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LogActionService implements ILogActionService {

    @Resource
    private LogActionRepository logActionRepository;

    @Override
    public LogActionRepository getRepository() {
        return logActionRepository;
    }
}
