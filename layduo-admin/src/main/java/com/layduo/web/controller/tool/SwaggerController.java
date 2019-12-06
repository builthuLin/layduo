package com.layduo.web.controller.tool;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.layduo.common.core.controller.BaseController;

/**
 * Swagger 接口
* @author layduo
* @createTime 2019年12月6日 上午10:20:35
*/
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController extends BaseController{

	@RequiresPermissions("tool:swagger:view")
	@GetMapping()
	public String index() {
		return redirect("/swagger-ui.html");
	}
}
