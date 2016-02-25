package cn.vfire.framework.spring.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/a/")
public class HelloWorldController {

	/**
	 * 返回视图对象
	 * @return
	 */
	@RequestMapping("index.html")
	public ModelAndView index() {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("message", "Hello World!");
		modelAndView.setViewName("index");

		return modelAndView;
	}

	/**
	 * 返回字符串
	 * @return
	 */
	@RequestMapping("index.txt")
	public String index_str() {
		return "index";
	}

	/**
	 * 返回对象
	 * @return
	 */
	@RequestMapping("index.json")
	public Map<String, String> index_json() {

		Map<String, String> m = new HashMap<String, String>();

		m.put("name", "小名");
		m.put("age", "13");

		return m;
	}

}
