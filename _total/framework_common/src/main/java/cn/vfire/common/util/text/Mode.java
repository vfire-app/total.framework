package cn.vfire.common.util.text;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 文本文件数据模型
 * 
 * @author ChenGang
 *
 */
public interface Mode {

	/**
	 * 将Bean对象处理成文本内容
	 * 
	 * @param bean
	 * @return
	 */
	public String transformContent(Object bean);


	/**
	 * 设置数据、文件时使用的字符集。
	 * 
	 * @return
	 */
	public Charset charset();

}
