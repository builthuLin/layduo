package com.layduo.framework.config;
/**
 * 日期时间格式化、适用于jdk1.8日期类型LocalDate 和 LocalDateTime
* @author layduo
* @createTime 2019年12月10日 下午5:25:00
* @learn to : https://blog.csdn.net/Linchack/article/details/88791785
*/

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class DateTimeFormatConfig {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> {
			builder.simpleDateFormat(DATETIME_FORMAT);
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
		};
	}
}
