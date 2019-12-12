package com.layduo.web.controller.monitor;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.annotation.Log;
import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.common.core.page.TableDataInfo;
import com.layduo.common.enums.BusinessType;
import com.layduo.common.utils.poi.ExcelUtil;
import com.layduo.system.domain.SysOperLog;
import com.layduo.system.service.ISysOperLogService;

/**
 * 系统操作日志
* @author layduo
* @createTime 2019年12月12日 下午3:02:40
*/
@Controller
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController{
	
	private String prefix = "monitor/operlog";
	
	@Autowired
	private ISysOperLogService operLogService;
	
	/**
	 * 访问操作日志页面
	 * @return
	 */
	@RequiresPermissions("monitor:operlog:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/operlog";
	}
	
	/**
	 * 获取操作日志列表
	 * @param operLog
	 * @return
	 */
	@RequiresPermissions("monitor:operlog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysOperLog operLog) {
		startPage();
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}
	
	/**
	 * 删除操作日志
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(operLogService.deleteOperLogByIds(ids));
	}
	
	/**
	 * 清空操作日志
	 * @return
	 */
	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return success();
	}
	
	/**
	 * 查看日志详情
	 * @param operId
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("monitor:operlog:detail")
	@GetMapping("/detail/{operId}")
	public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
		mmap.put("operLog", operLogService.selectOperLogById(operId));
		return prefix + "/detail";
	}
	
	/**
	 * 导出操作日志
	 * @param operLog
	 * @return
	 */
	@Log(title = "操作日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:operlog:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysOperLog operLog) {
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
		return util.exportExcel(list, "操作日志");
	}

}
