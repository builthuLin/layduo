package com.layduo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.layduo.common.annotation.DataSource;
import com.layduo.common.enums.DataSourceType;

/**
 * @author layduo
 * @createTime 2019年11月5日 下午3:21:19
 */
@Controller
public class DemoController {

	@RequestMapping("/sayHelloByMaster")
	@ResponseBody
	@DataSource(value = DataSourceType.MASTER)
	public String sayHelloByMaster() {
		return "Master say Hello World for you!";
	}
	
	@RequestMapping("/sayHelloBySlave")
	@ResponseBody
	@DataSource(value = DataSourceType.SLAVE)
	public String sayHelloBySlave() {
		return "Slave say Hello World for you!";
	}
}
