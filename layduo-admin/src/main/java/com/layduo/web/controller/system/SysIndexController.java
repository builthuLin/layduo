package com.layduo.web.controller.system;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.config.Global;
import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.framework.web.service.ConfigService;
import com.layduo.system.domain.SysMenu;
import com.layduo.system.domain.SysUser;
import com.layduo.system.service.ISysMenuService;

/**
 * 首页 业务处理
 * 
 * @author layduo
 */
@Controller
public class SysIndexController extends BaseController {
	
	@Autowired
	private ConfigService config;
	
	@Autowired
	private ISysMenuService menuService;

	// 系统首页
	@GetMapping("/index")
	public String index(ModelMap mmap) {
		// 取身份信息
		SysUser user = ShiroUtils.getSysUser();
		// 根据用户id取出菜单列表
		List<SysMenu> menus = menuService.selectMenusByUser(user);
		mmap.put("menus", menus);
		mmap.put("user", user);
		mmap.put("name", Global.getName());
		mmap.put("copyrightYear", Global.getCopyrightYear());
		mmap.put("demoEnabled", Global.isDemoEnabled());
		return "index";
	}
	
	//获取系统默认主题
	@GetMapping("/system/getThemeSkin")
	@ResponseBody
	public AjaxResult getThemeSkin(String sideTheme, String skinName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sideTheme", config.getKey(sideTheme));
		resultMap.put("skinName", config.getKey(skinName));
		return AjaxResult.success("查询成功!", resultMap);
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
		return "main_v1";
	}
	
	//时间监控
	@GetMapping("/system/timer")
	public String timer() {
		return "timer";
	}
	
	//地图监控
	@GetMapping("/system/leafletmap")
	public String leafletmap() {
		return "leafletmap";
	}
	
	//博客主页
	@GetMapping("/system/manager")
	public String manager() {
		return "manager";
	}
	
	
	@GetMapping("/system/pdfview")
	public String pdfview() {
		return "test";
	}
	
	//文件预览
	@RequestMapping(value = "/system/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(String fileName,HttpServletRequest request,HttpServletResponse response) {
 
		String filePath = Global.getUploadPath();
		
        File file = new File(filePath + "/" + fileName);
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                logger.error("返回pdf文件流异常：" + e.getMessage());
            }
 
        }else{
        	logger.error("未找到相关pdf文件：" + filePath + fileName);
            return;
        }
    }
}
