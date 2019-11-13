package com.layduo.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.layduo.common.core.controller.BaseController;

/**
 * 参数配置 信息操作处理
 * 
 * @author layduo
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
	private String prefix = "system/config";

	@GetMapping()
	public String config() {
		return prefix + "/config";
	}

}
