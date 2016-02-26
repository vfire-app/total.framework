package cn.vfire.framework.spring.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.vfire.framework.spring.mvc.mode.BookMode;
import cn.vfire.framework.spring.mvc.view.Result;
import cn.vfire.framework.spring.mvc.view.XModelAndView;

@RequestMapping("/restfull")
@RestController
public class RestFullController {

	@RequestMapping(path = "/book", method = RequestMethod.GET)
	@ResponseBody
	public Result book(String username) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2016-02-01");

		XModelAndView modeView = new XModelAndView();

		BookMode book = new BookMode();
		{
			book.setName("迪士尼双语经典电影故事：疯狂动物城");
			book.setPublisher("中央广播电视大学出版社");
			book.setNumberOfPages(96);
			book.setOpeningtime(date);
			book.setRevision(1);
		}

		modeView.addObject("book", book);

		return modeView.toResult();
	}
	
	@RequestMapping(path = "/book2", method = RequestMethod.GET)
	@ResponseBody
	public Result book2(String username) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2016-02-01");

		XModelAndView modeView = new XModelAndView();

		BookMode book = new BookMode();
		{
			book.setName("迪士尼双语经典电影故事：疯狂动物城");
			book.setPublisher("中央广播电视大学出版社");
			book.setNumberOfPages(96);
			book.setOpeningtime(date);
			book.setRevision(1);
		}

		modeView.addObject("book", book);

		return modeView.toResult();
	}

}
