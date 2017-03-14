package com.yiji.ypayment.biz.service.manage.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.manage.ConfigService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.common.Config;
import com.yiji.ypayment.dal.enums.ConfigKey;
import com.yiji.ypayment.dal.repository.common.ConfigDao;

@Service("configService")
public class ConfigServiceImpl extends EntityServiceImpl<Config, ConfigDao> implements ConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	
	@Override
	public Config findByConfigKey(ConfigKey configKey) {
		return getEntityDao().findByConfigKey(configKey);
	}
	
}
