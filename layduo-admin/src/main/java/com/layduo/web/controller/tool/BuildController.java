package com.layduo.web.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.layduo.common.core.controller.BaseController;

/**
 * 表单构建 控制层
* @author layduo
* @createTime 2019年12月6日 下午3:31:01
*/
@Controller
@RequestMapping("/tool/build")
public class BuildController extends BaseController {

	private String prefix = "tool/build";
	
	@RequiresPermissions("tool:build:view")
	@GetMapping()
	public String build() {
		return prefix + "/build";
	}
}
