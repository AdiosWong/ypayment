package com.yiji.ypayment.test.base;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.yiji.boot.core.Apps;
import com.yiji.boot.test.AppWebTestBase;
import com.yiji.ypayment.Main;

/**
 *
 * faZheng
 */
@SpringApplicationConfiguration(classes = Main.class)
public class TestBase extends AppWebTestBase {
	
	protected static final String PROFILE = "stest";
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	static {
		Apps.setProfileIfNotExists(PROFILE);
	}
}
