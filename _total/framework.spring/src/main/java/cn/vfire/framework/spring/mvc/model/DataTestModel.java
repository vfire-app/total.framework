package cn.vfire.framework.spring.mvc.model;

import lombok.Getter;
import lombok.Setter;

public class DataTestModel {
	@Getter
	@Setter
	private PhoneNumberModel phoneNumber;// String->自定义对象的转换测试
}