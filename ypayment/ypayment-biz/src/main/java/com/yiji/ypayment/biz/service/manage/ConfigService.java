package com.yiji.ypayment.biz.service.manage;

import com.yiji.ypayment.common.service.EntityService;
import com.yiji.ypayment.dal.entity.common.Config;
import com.yiji.ypayment.dal.enums.ConfigKey;

public interface ConfigService extends EntityService<Config> {
	
	Config findByConfigKey(ConfigKey configKey);
}
