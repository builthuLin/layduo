package com.layduo.web.controller.system;

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
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysNotice;
import com.layduo.system.service.ISysNoticeService;

/**
 * 通知公告操作控制层
* @author layduo
* @createTime 2019年12月16日 下午4:13:06
*/
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController{
	
	private String prefix = "system/notice";
	
	@Autowired
	private ISysNoticeService noticeService;
	
	/**
	 * 访问通知公告页面
	 * @return
	 */
	@RequiresPermissions("system:notice:view")
	@GetMapping()
	public String notice() {
		return prefix + "/notice";
	}

	/**
	 * 查询通知公告列表
	 * @param notice
	 * @return
	 */
	@RequiresPermissions("system:notice:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysNotice notice) {
		startPage();
		List<SysNotice> list = noticeService.selectNoticeList(notice);
		return getDataTable(list);
	}
	
	/**
	 * 新增通知公告
	 * @return
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}
	
	/**
	 * 新增保存通知公告
	 * @param notice
	 * @return
	 */
	@Log(title = "通知公告", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:notice:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysNotice notice) {
		notice.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.insertNotice(notice));
	}
	
	/**
	 * 修改通知公告
	 * @param noticeId
	 * @param mmap
	 * @return
	 */
	@GetMapping("/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
		mmap.put("notice", noticeService.selectNoticeById(noticeId));
		return prefix + "/edit";
	}
	
	/**
	 * 修改保存通知公告
	 * @param notice
	 * @return
	 */
	@Log(title = "通知公告", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:notice:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysNotice notice) {
		notice.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(noticeService.updateNotice(notice));
	}
	
	/**
	 * 删除通知公告
	 * @param ids
	 * @return
	 */
	@Log(title = "通知公告", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:notice:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(noticeService.deleteNoticeByIds(ids));
	}
}
