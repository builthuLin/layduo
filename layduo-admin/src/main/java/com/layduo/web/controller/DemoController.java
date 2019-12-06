package com.layduo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.annotation.DataSource;
import com.layduo.common.annotation.WebLog;
import com.layduo.common.enums.DataSourceType;
import com.layduo.web.domain.User;
import com.layduo.web.service.UserService;

/**
 * @author layduo
 * @createTime 2019年11月5日 下午3:21:19
 */
@Controller
@RequestMapping("/api")
public class DemoController {
	
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private UserService userService;
	
	//访问主数据库
	@RequestMapping("/sayHelloByMaster")
	@ResponseBody
	@DataSource(value = DataSourceType.MASTER)
	public String sayHelloByMaster() {
		return "Master say Hello World for you!";
	}
	
	//访问从数据库
	@RequestMapping("/sayHelloBySlave")
	@ResponseBody
	@DataSource(value = DataSourceType.SLAVE)
	public String sayHelloBySlave() {
		return "Slave say Hello World for you!";
	}
	
	@RequestMapping("/testWebLog/{name}")
	@ResponseBody
	@WebLog(description = "测试自定义注解@WebLog")
	public String testWebLog(@PathVariable("name") String name) {
		return "Welcome to " + name + "!";
	}
	
	@RequestMapping(value = "/selectAllUser")
	@ResponseBody
	@WebLog(description = "查询所有测试用户")
	public List<User> selectAllUser() {
		return userService.selectAllUser();
	}
	
}
