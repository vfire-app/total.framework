package cn.vfire.framework.spring.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;


public class JsonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(	Map<String, Object> model, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
		
		
		System.out.println("JsonView:"+model);
		

	}

}
