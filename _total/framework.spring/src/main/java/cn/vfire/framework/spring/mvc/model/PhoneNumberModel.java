package cn.vfire.framework.spring.mvc.model;

import lombok.Getter;
import lombok.Setter;

public class PhoneNumberModel {

	/** 区号 */
	@Getter
	@Setter
	private String areaCode;

	/** 电话号 */
	@Getter
	@Setter
	private String phoneNumber;
}
