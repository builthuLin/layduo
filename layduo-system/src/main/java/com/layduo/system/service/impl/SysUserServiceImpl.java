package com.layduo.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.layduo.common.constant.UserConstants;
import com.layduo.common.utils.StringUtils;
import com.layduo.system.domain.SysPost;
import com.layduo.system.domain.SysRole;
import com.layduo.system.domain.SysUser;
import com.layduo.system.mapper.SysPostMapper;
import com.layduo.system.mapper.SysRoleMapper;
import com.layduo.system.mapper.SysUserMapper;
import com.layduo.system.service.ISysUserService;

/**
 * 用户 业务层处理
 * 
 * @author layduo
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysRoleMapper roleMapper;
	
	@Autowired
	private SysPostMapper postMapper;
	

	/**
	 * 根据条件分页查询用户列表
	 * 
	 * @param user
	 *            用户信息
	 * @return 用户信息集合信息
	 */
	@Override
	public List<SysUser> selectUserList(SysUser user) {
		return userMapper.selectUserList(user);
	}

	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @param 用户对象信息
	 */
	@Override
	public SysUser selectUserByLoginName(String userName) {
		return userMapper.selectUserByLoginName(userName);
	}

	/**
	 * 通过手机号码查询用户
	 * 
	 * @param phoneNumber
	 *            手机号码
	 */
	@Override
	public SysUser selectUserByPhoneNumber(String phoneNumber) {
		return userMapper.selectUserByPhoneNumber(phoneNumber);
	}

	/**
	 * 通过电子邮箱查询用户
	 * 
	 * @param email
	 *            邮箱
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserByEmail(String email) {
		return userMapper.selectUserByEmail(email);
	}

	/**
	 * 通过用户ID查询用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户对象信息
	 */
	@Override
	public SysUser selectUserById(Long userId) {
		return userMapper.selectUserById(userId);
	}

	/**
	 * 用户状态修改
	 * 
	 * @param user
	 *            用户信息
	 * @return 用户对象信息
	 */
	@Override
	public int changeStatus(SysUser user) {
		return userMapper.updateUser(user);
	}

	/**
	 * 修改保存用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 结果
	 */
	@Override
	public int updateUser(SysUser user) {
		return 0;
	}

	/**
	 * 修改用户个人详细信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 结果
	 */
	@Override
	public int updateUserInfo(SysUser user) {
		return userMapper.updateUser(user);
	}

	@Override
	public int resetUserPwd(SysUser user) {
		return updateUserInfo(user);
	}

	@Override
	public List<SysUser> selectAllocatedList(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysUser> selectUnallocatedList(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteUserById(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUserByIds(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertUser(SysUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 校验登录名称是否唯一
	 */
	@Override
	public String checkLoginNameUnique(String loginName) {
		int count = userMapper.checkLoginNameUnique(loginName);
		if (count > 0) {
			return UserConstants.USER_NAME_NOT_UNIQUE;
		}
		return UserConstants.USER_EMAIL_UNIQUE;
	}

	/**
	 * 校验手机号码是否唯一
	 */
	@Override
	public String checkPhoneUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_PHONE_NOT_UNIQUE;
		}
		return UserConstants.USER_PHONE_UNIQUE;
	}

	/**
	 * 校验邮箱是否唯一
	 */
	@Override
	public String checkEmailUnique(SysUser user) {
		Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		SysUser info = userMapper.checkEmailUnique(user.getEmail());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_EMAIL_NOT_UNIQUE;
		}
		return UserConstants.USER_EMAIL_UNIQUE;
	}

	@Override
	public void checkUserAllowed(SysUser user) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 查询用户所属角色组
	 */
	@Override
	public String selectUserRoleGroup(Long userId) {
		List<SysRole> list= roleMapper.selectRolesByUserId(userId);
		StringBuffer idsStr = new StringBuffer();
		for (SysRole role : list) {
			idsStr.append(role.getRoleName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * 查询用户所属岗位组
	 */
	@Override
	public String selectUserPostGroup(Long userId) {
		List<SysPost> list = postMapper.selectPostsByUserId(userId);
		StringBuffer idsStr = new StringBuffer();
		for (SysPost post : list) {
			idsStr.append(post.getPostName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	@Override
	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
		// TODO Auto-generated method stub
		return null;
	}

}
