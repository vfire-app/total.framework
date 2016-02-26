package cn.vfire.framework.spring.mvc.view;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Result {

	@Getter
	@Setter
	private int code = 0;

	@Getter
	@Setter
	private String error = "SUCCESS";

	@Getter
	@Setter
	private String msgcode = "0000";

	@Getter
	@Setter
	private String msg = "";

	@Getter
	@Setter
	private Object data;

	public Result() {
		this.code = 0;
		this.error = "";
	}

	public Result(Object data) {
		this.code = 0;
		this.error = "";
		this.data = data;
	}

	public Result(String error) {
		this.code = 1;
		this.error = error;
	}

	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

}
