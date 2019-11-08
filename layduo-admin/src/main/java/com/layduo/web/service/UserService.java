package com.layduo.web.service;

import java.util.List;

import com.layduo.web.domain.User;

/**
* @author layduo
* @createTime 2019年11月8日 下午4:38:21
*/
public interface UserService {

	List<User> selectAllUser();
}
