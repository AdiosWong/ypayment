package com.yiji.ypayment.dal.repository.common;

import com.yiji.ypayment.common.dao.jpa.EntityJpaDao;
import com.yiji.ypayment.dal.entity.common.Config;
import com.yiji.ypayment.dal.enums.ConfigKey;

public interface ConfigDao extends EntityJpaDao<Config, Long> {
	
	Config findByConfigKey(ConfigKey configKey);
	
}
