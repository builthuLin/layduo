package com.layduo.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.system.domain.SysUser;
import com.layduo.system.service.ISysUserService;

/**
 * 用户信息
* @author layduo
* @createTime 2019年12月6日 下午5:44:00
*/
@Controller
@RequestMapping("/system/user")
public class SysUserController {

	private String prefix = "system/user";
	
	@Autowired
	private ISysUserService userService;
	
	/**
	 * 校验用户名是否唯一
	 * @param user
	 * @return
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(SysUser user) {
		return userService.checkLoginNameUnique(user.getLoginName()); 
	}
	
	/**
	 * 校验手机号码是否唯一
	 * @param user
	 * @return
	 */
	@PostMapping("/checkPhoneUnique")
	@ResponseBody
	public String checkPhoneUnique(SysUser user) {
		return userService.checkPhoneUnique(user);
	}
	
	/**
	 * 校验邮箱是否唯一
	 * @param user
	 * @return
	 */
	@PostMapping("/checkEmailUnique")
	@ResponseBody
	public String checkEmailUnique(SysUser user) {
		return userService.checkEmailUnique(user);
	}
}
