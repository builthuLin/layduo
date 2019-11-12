package com.layduo.system.service;

import java.util.List;

import com.layduo.system.domain.SysUser;

/**
 * 用户 业务层
 * 
 * @author layduo
 */
public interface ISysUserService {
	/**
	 * 根据条件分页查询用户列表
	 * 
	 * @param user
	 *            用户信息
	 * @return 用户信息集合信息
	 */
	public List<SysUser> selectUserList(SysUser user);


	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户对象信息
	 */
	public SysUser selectUserByLoginName(String userName);

	/**
	 * 通过手机号码查询用户
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return 用户对象信息
	 */
	public SysUser selectUserByPhoneNumber(String phoneNumber);

	/**
	 * 通过邮箱查询用户
	 * 
	 * @param email
	 *            邮箱
	 * @return 用户对象信息
	 */
	public SysUser selectUserByEmail(String email);
	
	/**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);
	
	/**
	 * 修改用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 结果
	 */
	public int updateUser(SysUser user);

	/**
     * 修改用户详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(SysUser user);	

	/**
	 * 用户状态修改
	 * 
	 * @param user
	 *            用户信息
	 * @return 结果
	 */
	public int changeStatus(SysUser user);
}
