package cn.vfire.framework.spring.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/ftl")
@RestController
public class FreeMarkerController {

	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("freemarker");
	}

}
