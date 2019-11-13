package com.layduo.web.controller.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.layduo.common.core.controller.BaseController;

/**
 * druid 监控
 * 
 * @author layduo
 */
@Controller
@RequestMapping("/monitor/data")
public class DruidController extends BaseController {
	private String prefix = "/druid";

	@GetMapping()
	public String index() {
		return redirect(prefix + "/index");
	}
}
