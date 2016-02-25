package cn.vfire.framework.spring.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.vfire.framework.spring.mvc.model.DataTestModel;
import cn.vfire.framework.spring.mvc.model.DemoObjModel;
import cn.vfire.framework.spring.mvc.model.UserModel;

@RestController
public class LoginController {

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/login2")
	public String login2() {
		return "login";
	}

	@RequestMapping("/vali1")
	public ModelAndView vali1(@Valid UserModel user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			System.out.println(result.getErrorCount());
			user.setNumbcode(0);
		}

		System.out.println(user);

		model.addAttribute("user", user);

		return new ModelAndView("rs");
	}

	@RequestMapping("/vali3")
	public ModelAndView vali3(@Valid UserModel user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			user.setNumbcode(0);
			model.addAttribute("error", result.getFieldErrors().toString());
		}

		model.addAttribute("user", user);

		return new ModelAndView("rs");
	}

	@RequestMapping("/vali2")
	public String vali2(@Valid UserModel user, BindingResult result, @Valid UserModel user1, BindingResult result2) {

		if (result.hasErrors()) {
			System.out.println(result.getErrorCount());
		}

		System.out.println(user);
		System.out.println(user1);

		return "rs";
	}

	@RequestMapping(value = "/text", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String text() {
		String text = "text 你好";
		return text;
	}

	@RequestMapping(value = "/say")
	@ResponseBody
	public Map<String, Object> say(String msg) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "xiaohong");
		map.put("cname", "小红");
		map.put("msg", msg);

		System.out.println(map);

		return map;
	}

	@RequestMapping(value = "/say2")
	public Map<String, Object> say2(DataTestModel msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "xiaohong");
		map.put("cname", "小红");
		map.put("msg", msg.toString());
		return map;
	}

	@RequestMapping(value = "/jsonview")
	@ResponseBody
	public String jsonview(Model model) {

		DemoObjModel demo = new DemoObjModel();
		demo.setId(1000L);
		demo.setName("小红");
		demo.setFlag(false);

		model.addAttribute("DemoObjModel", demo);

		return "index";
	}

	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception e) {
		// 添加自己的异常处理逻辑，如日志记录　　　
		request.setAttribute("exceptionMessage", e.getMessage());
		// 根据不同的异常类型进行不同处理
		if (e instanceof BindException)
			return "testerror";
		else
			return "error";
	}

}
