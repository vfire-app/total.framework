package cn.vfire.framework.spring.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.vfire.framework.spring.mvc.model.DemoObjModel;
import cn.vfire.framework.spring.mvc.view.JsonView;

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

}
