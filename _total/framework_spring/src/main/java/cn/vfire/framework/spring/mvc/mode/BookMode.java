package cn.vfire.framework.spring.mvc.mode;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class BookMode {

	/** 书名 */
	@Getter
	@Setter
	private String name;

	/** 出版社 */
	@Getter
	@Setter
	private String publisher;

	/** 印刷次数 */
	@Getter
	@Setter
	private int revision;

	/** 出版时间 */
	@Getter
	@Setter
	private Date openingtime;

	/** 页数 */
	@Getter
	@Setter
	private int numberOfPages;

}
