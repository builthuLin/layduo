package com.layduo.web.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.layduo.common.annotation.Log;
import com.layduo.common.config.Global;
import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.common.core.domain.AjaxResult.Type;
import com.layduo.common.enums.BusinessType;
import com.layduo.common.enums.OperatorType;
import com.layduo.common.utils.StringUtils;
import com.layduo.common.utils.file.FileUploadUtils;
import com.layduo.framework.shiro.service.SysPasswordService;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysUser;
import com.layduo.system.service.ISysUserService;

/**
 * 个人信息 业务处理
* @author layduo
* @createTime 2019年11月25日 上午9:58:12
*/
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);
	
	private String prefix = "system/user/profile";
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private SysPasswordService passwordService;
	
	/**
	 * 跳转重置密码页面
	 * @param mmap
	 * @return
	 */
	@GetMapping("/resetPwd")
	public String resetPwd(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
	}
	
	/**
	 * 验证密码
	 * @param password
	 * @return
	 */
	@GetMapping("/checkPassword")
	@ResponseBody
    public boolean checkPassword(String password) {
    	SysUser user = ShiroUtils.getSysUser();
    	if (passwordService.matches(user, password)) {
			return true;
		}
    	return false;
    }
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Log(title = "重置密码", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(String oldPassword, String newPassword) {
		SysUser user = ShiroUtils.getSysUser();
		if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword)) {
			user.setSalt(ShiroUtils.randomSalt());
			user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
			if (userService.resetUserPwd(user) > 0) {
				ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
				return success();
			}
			return error();
		}else {
			return error("修改密码失败，旧密码错误");
		}
	}
	
	/**
	 * 个人信息
	 * @param mmap
	 * @return
	 */
	@GetMapping()
	public String profile(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", user);
		mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
		mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
		return prefix + "/profile";
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	@ResponseBody
	public AjaxResult update(SysUser user) {
		SysUser currentUser = ShiroUtils.getSysUser();
		currentUser.setUserName(user.getUserName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhonenumber(user.getPhonenumber());
		currentUser.setSex(user.getSex());
		if (userService.updateUserInfo(currentUser) > 0) {
			ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
			return success();
		}
		return error();
	}
	
	/**
	 * 跳转修改头像页面
	 * @param mmap
	 * @return
	 */
	@GetMapping("/avatar")
	public String avatar(ModelMap mmap) {
		SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/avatar";
	}
	
	/**
	 * 保存用户头像
	 * @param file
	 * @return
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/updateAvatar")
	@ResponseBody
	public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
		SysUser currentUser = ShiroUtils.getSysUser();
		try {
			if (!file.isEmpty()) {
				String avatar = FileUploadUtils.upload(Global.getAvatarPath(), file);
				currentUser.setAvatar(avatar);
				if (userService.updateUserInfo(currentUser) > 0) {
					ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
					return success();
				}
			}
			return error();
		} catch (Exception e) {
			log.error("修改头像失败!", e);
			return error(e.getMessage());
		}
	}
}
