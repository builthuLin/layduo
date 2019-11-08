package com.layduo.web.domain;
/**
* @author layduo
* @createTime 2019年11月8日 下午4:34:43
*/
public class User {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String userName;
	
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "TUser [userId=" + userId + ", userName=" + userName + ", password=" + password + "]";
	}
	
	
}
