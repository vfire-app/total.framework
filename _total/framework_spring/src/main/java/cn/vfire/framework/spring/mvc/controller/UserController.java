package cn.vfire.framework.spring.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.vfire.framework.spring.mvc.mode.UserMode;
import cn.vfire.framework.spring.mvc.view.XModelAndView;

@RequestMapping("/api")
@RestController
public class UserController {

	@RequestMapping(path = "/userAdd.api", method = RequestMethod.POST,produces="application/json; charset=UTF-8")
	@ResponseBody
	public String userAdd(UserMode user) {

		XModelAndView modeView = new XModelAndView();
		
		modeView.addObject("user", user);

		System.out.println(String.format("添加用户%s完成", user.getName()));

		return modeView.toJson();
	}

	@RequestMapping(path = "/userQuery.api", method = RequestMethod.GET)
	public String userQuery(String username) {

		XModelAndView modeView = new XModelAndView();

		UserMode user = new UserMode();
		{
			user.setUsername(username);
			user.setName("小红");
			user.setAge(22);
			user.setSex(0);
		}

		modeView.addObject("user", user);

		System.out.println(String.format("查询用户名为%s用户 信息成功", user.getUsername()));

		return modeView.toJson();
	}

	@RequestMapping(path = "/userSave.api", method = RequestMethod.POST)
	public String userSave(UserMode user) {

		XModelAndView modeView = new XModelAndView();

		user.setAge(23);

		modeView.addObject("user", user);

		System.out.println(String.format("更新用户名为%s用户信息成功", user.getUsername()));

		return modeView.toJson();
	}

	@RequestMapping(path = "/userDel.api", method = RequestMethod.GET)
	public String userDel(String username) {

		XModelAndView modeView = new XModelAndView();

		System.out.println(String.format("删除用户名为%s用户信息成功", username));

		return modeView.toJson();
	}

}
