package cn.vfire.framework.spring.mvc.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class UserModel {

	@Setter
	private String username;

	@NotBlank(message="密码不能为空")  
	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private int numbcode;

	@NotNull(message = "用户名不能为空")
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return String.format("username=%s,password=%s,numbcode=%d", this.username, this.password, this.numbcode);
	}

}
