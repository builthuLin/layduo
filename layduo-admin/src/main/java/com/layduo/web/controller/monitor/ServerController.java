package com.layduo.web.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.layduo.common.core.controller.BaseController;
import com.layduo.framework.web.domain.Server;

/**
 * 服务监控
* @author layduo
* @createTime 2019年12月17日 上午10:36:14
*/
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController{

	private String prefix = "monitor/server";
	
	/**
	 * 访问服务监控页面
	 * @param mmap
	 * @return
	 * @throws Exception 
	 */
	@RequiresPermissions("monitor:server:view")
	@GetMapping()
	public String server(ModelMap mmap) throws Exception {
		Server server = new Server();
		server.copyTo();
		mmap.put("server", server);
		return prefix + "/server";
	}
}
