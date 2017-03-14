package com.yiji.ypayment.biz.service.ypayment.impl;

import org.springframework.stereotype.Service;

import com.yiji.ypayment.biz.service.ypayment.PlatformTypeService;
import com.yiji.ypayment.common.service.EntityServiceImpl;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.repository.business.PlatformTypeDao;

@Service("platformTypeService")
public class PlatformTypeServiceImpl extends EntityServiceImpl<PlatformType, PlatformTypeDao> implements
																								PlatformTypeService {
	@Override
	public PlatformType findByPlatformType(String platformType) {
		return getEntityDao().findByPlatformType(platformType);
	}
}
