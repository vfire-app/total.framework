package cn.vfire.framework.spring.mvc.view;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public class XModelAndView extends ModelAndView {

	public XModelAndView() {
		super();
	}

	public XModelAndView(String viewName) {
		super(viewName);
	}

	public XModelAndView(String viewName, Map<String, ?> model) {
		super(viewName, model);
	}

	public XModelAndView(String viewName, String modelName, Object modelObject) {
		super(viewName, modelName, modelObject);
	}

	public XModelAndView(View view) {
		super(view);
	}

	public XModelAndView(View view, Map<String, ?> model) {
		super(view, model);
	}

	public XModelAndView(View view, String modelName, Object modelObject) {
		super(view, modelName, modelObject);
	}

	/**
	 * 返回JSON报文。
	 * 
	 * @return
	 */
	public String toJson() {

		Result rs = new Result();

		Map<String, Object> mode = super.getModel();
		if (mode != null && mode.isEmpty() == false) {
			if (mode.size() == 1) {
				String k = mode.keySet().iterator().next();
				rs.setData(mode.get(k));
			}
			if (mode.size() > 1) {
				rs.setData(mode);
			}
		}

		return rs.toJson();
	}

}
