package com.code.proter.user.logger.service;

import com.code.proter.user.logger.repository.LogActionRepository;
import com.code.proter.common.base.core.service.BaseService;
import com.code.proter.user.domain.logaction.LogAction;

public interface ILogActionService extends BaseService<Long, LogAction, LogActionRepository> {


}
