package com.layduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 项目启动程序
 * 
 * @author layduo
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LayduoApplication {
	public static void main(String[] args) {
		SpringApplication.run(LayduoApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ  \n");
	}
}
