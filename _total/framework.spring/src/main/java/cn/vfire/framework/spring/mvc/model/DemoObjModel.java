package cn.vfire.framework.spring.mvc.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "demo")
public class DemoObjModel {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private boolean flag ;

}
