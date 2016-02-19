package cn.vfire.framework.spring.mvc.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class JsonViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {

		System.out.println(viewName);
		System.out.println(locale);

		return new JsonView();
	}

}
