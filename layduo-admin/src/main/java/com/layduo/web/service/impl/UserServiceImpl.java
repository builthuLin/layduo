package com.layduo.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.layduo.common.annotation.DataSource;
import com.layduo.common.enums.DataSourceType;
import com.layduo.web.domain.User;
import com.layduo.web.mapper.UserMapper;
import com.layduo.web.service.UserService;

/**
* @author layduo
* @createTime 2019年11月8日 下午4:39:09
*/
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper; 

	@Override
	@DataSource(value = DataSourceType.SLAVE)
	public List<User> selectAllUser() {
		return userMapper.selectAllUser();
	}

}
