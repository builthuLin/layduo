package com.layduo.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.layduo.system.domain.SysUser;
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

}
