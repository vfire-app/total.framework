package cn.vfire.framework.spring.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TestHandlerInterceptorAdapter implements HandlerInterceptor {

	@Override
	public boolean preHandle(	HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {

		String url = request.getRequestURI();
		String queryString = request.getQueryString();

		System.out.println(url);
		System.out.println(queryString);

		return true;
	}


	@Override
	public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler,
							ModelAndView modelAndView) throws Exception {

	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception ex) throws Exception {

	}

}
