package com.layduo.web.controller.monitor;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.annotation.Log;
import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.common.core.page.TableDataInfo;
import com.layduo.common.enums.BusinessType;
import com.layduo.common.utils.poi.ExcelUtil;
import com.layduo.framework.shiro.service.SysPasswordService;
import com.layduo.system.domain.SysLogininfor;
import com.layduo.system.service.ISysLogininforService;

/**
 * 登录日志记录
* @author layduo
* @createTime 2019年12月16日 下午2:41:54
*/
@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController{

	private String prefix = "monitor/logininfor";
	
	@Autowired
	private ISysLogininforService logininforService;
	
	@Autowired
	private SysPasswordService passwordService;
	/**
	 * 访问日志记录页面
	 * @return
	 */
	@RequiresPermissions("monitor:logininfor:view")
	@GetMapping()
	public String logininfor() {
		return prefix + "/logininfor";
	}
	
	/**
	 * 查询日志记录列表
	 * @param logininfor
	 * @return
	 */
	@RequiresPermissions("monitor:logininfor:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysLogininfor logininfor) {
		startPage();
		List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		return getDataTable(list);
	}
	
	/**
	 * 删除登录日志
	 * @param ids
	 * @return
	 */
	@Log(title = "登录日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("monitor:logininfor:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(logininforService.deleteLogininforByIds(ids));
	}
	
	/**
	 * 清空登录日志
	 * @return
	 */
	@Log(title = "登录日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("monitor:logininfor:remove")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		logininforService.cleanLogininfor();
		return success();
	}
	
	/**
	 * 导出登录日志
	 * @param logininfor
	 * @return
	 */
	@Log(title = "登录日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:logininfor:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysLogininfor logininfor) {
		List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
		return util.exportExcel(list, "登录日志");
	}
	
	/**
	 * 解锁已锁用户
	 * @param loginName
	 * @return
	 */
	@Log(title = "账户解锁", businessType = BusinessType.OTHER)
	@RequiresPermissions("monitor:logininfor:unlock")
	@PostMapping("/unlock")
	@ResponseBody
	public AjaxResult unlock(String loginName) {
		passwordService.unlock(loginName);
		return success();
	}
}
