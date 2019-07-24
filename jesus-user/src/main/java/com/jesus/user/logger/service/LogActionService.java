package com.jesus.user.logger.service;

import com.jesus.common.base.core.service.BaseService;
import com.jesus.user.logger.repository.LogActionRepository;
import com.jesus.user.model.logaction.LogAction;
import com.jesus.user.model.resource.Resource;

public interface LogActionService extends BaseService<Long, LogAction, LogActionRepository> {


}
