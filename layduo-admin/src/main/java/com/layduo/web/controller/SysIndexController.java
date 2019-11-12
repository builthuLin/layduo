package com.layduo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.layduo.common.config.Global;
import com.layduo.common.core.controller.BaseController;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysUser;

/**
 * 首页 业务处理
 * 
 * @author layduo
 */
@Controller
public class SysIndexController extends BaseController {

	// 系统首页
	@GetMapping("/index")
	public String index(ModelMap mmap) {
		// 取身份信息
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", user);
		mmap.put("copyrightYear", Global.getCopyrightYear());
		return "index";
	}

	// 切换主题
	@GetMapping("/system/switchSkin")
	public String switchSkin(ModelMap mmap) {
		return "skin";
	}

	// 系统介绍
	@GetMapping("/system/main")
	public String main(ModelMap mmap) {
		mmap.put("version", Global.getVersion());
		return "main";
	}
}
