package com.yiji.ypayment.web.boss.management.configment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yiji.ypayment.biz.service.manage.ConfigService;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.dal.entity.common.Config;
import com.yiji.ypayment.dal.enums.ConfigKey;
import com.yiji.ypayment.web.common.web.AbstractJQueryEntityController;

/**
 * 属性管理
 */
@Controller
@RequestMapping(value = "boss/ypayment/management/configment/config")
public class ConfigController extends AbstractJQueryEntityController<Config, ConfigService> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigService configService;
	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		super.referenceData(request, model);
		model.put("allConfigKeys", ConfigKey.values());
		model.put("phoneSeparator", Constant.PHONE_SEPARATOR);
	}
	
	@Override
	public String saveList(HttpServletRequest request, HttpServletResponse response, Model model,
							RedirectAttributes redirectAttributes) {
		// 不能保存相同的key
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			String key = request.getParameter("configKey");
			ConfigKey configKey = ConfigKey.getByKey(key);
			Config config = configService.findByConfigKey(configKey);
			if (config != null) {
				return null;
			}
		}
		
		return super.saveList(request, response, model, redirectAttributes);
	}
	
}
