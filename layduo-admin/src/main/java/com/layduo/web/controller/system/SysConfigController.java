package com.layduo.web.controller.system;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.apache.bcel.classfile.Module.Export;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.annotation.Log;
import com.layduo.common.constant.UserConstants;
import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.common.core.page.TableDataInfo;
import com.layduo.common.enums.BusinessType;
import com.layduo.common.utils.poi.ExcelUtil;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysConfig;
import com.layduo.system.service.ISysConfigService;

import repackage.EditBuildScript;

/**
 * 参数配置 信息操作处理
 * 
 * @author layduo
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
	
	private String prefix = "system/config";
	
	@Autowired
	private ISysConfigService configService;

	/**
	 * 访问系统配置页面
	 * @return
	 */
	@RequiresPermissions("system:config:view")
	@GetMapping()
	public String config() {
		return prefix + "/config";
	}
	
	/**
	 * 查询参数配置列表
	 * @param config
	 * @return
	 */
	@RequiresPermissions("system:config:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysConfig config) {
		startPage();
		List<SysConfig> list = configService.selectConfigList(config);
		return getDataTable(list);
	}
	
	/**
	 * 新增参数配置
	 * @return
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}
	
	/**
	 * 新增保存
	 * @param config
	 * @return
	 * @Validated配合SysConfig中属性注解一起校验
	 */
	@RequiresPermissions("system:config:add")
	@Log(title = "参数管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysConfig config) {
		//校验参数键名是否唯一
		if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(configService.insertConfig(config));
	}
	
	/**
	 * 修改参数配置
	 * @param configId
	 * @param mmap
	 * @return
	 */
	@GetMapping("/edit/{configId}")
	public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
		mmap.put("config", configService.selectConfigById(configId));
		return prefix + "/edit";
	}
	
	/**
	 * 修改保存
	 * @param config
	 * @return
	 */
	@RequiresPermissions("system:config:edit")
	@Log(title = "参数管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysConfig config) {
		if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(configService.updateConfig(config));
	}
	
	/**
	 * 删除参数配置
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("system:config:remove")
	@Log(title = "参数管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(configService.deleteConfigByIds(ids));
	}
	
	/**
	 * 参数配置导出
	 * @param config
	 * @return
	 */
	@Log(title = "参数管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:config:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysConfig config) {
		List<SysConfig> list = configService.selectConfigList(config);
		ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
		return util.exportExcel(list, "参数配置数据");
	}
	
	/**
	 * 检验参数键名是否唯一
	 * @param config
	 * @return
	 */
	@PostMapping("/checkConfigKeyUnique")
	@ResponseBody
	public String checkConfigKeyUnique(SysConfig config) {
		return configService.checkConfigKeyUnique(config);
	}

}
