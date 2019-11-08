package com.layduo.web.mapper;
/**
* @author layduo
* @createTime 2019年11月8日 下午4:37:28
*/

import java.util.List;

import com.layduo.web.domain.User;

public interface UserMapper {

	List<User> selectAllUser();
}
