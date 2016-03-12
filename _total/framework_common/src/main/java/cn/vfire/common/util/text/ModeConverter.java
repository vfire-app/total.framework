package cn.vfire.common.util.text;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public interface ModeConverter {

	/**
	 * 得到解析器支持的mode对象的类型
	 * 
	 * @return
	 */
	public List<Type> supportModes();


	/**
	 * 判断是否支持
	 * 
	 * @return
	 */
	public boolean support(File file, Mode mode);


	/**
	 * 测试该文件能否读
	 * 
	 * @param file
	 * @param mode
	 * @return
	 */
	public boolean canRead(String fulltext, Mode mode);


	/**
	 * 测试该文件能否写
	 * 
	 * @param file
	 * @param fulltext
	 * @param mode
	 * @return
	 */
	public boolean canWrite(String fulltext, Mode mode);


	/**
	 * 计算文件大小
	 * 
	 * @param file
	 * @param unit
	 *            B\K\M\G
	 * @return
	 */
	public long fileSize(File file, char unit);


	/**
	 * 内部，根据mode读取文件数据
	 * 
	 * @param fulltext
	 *            文件正文
	 * @param mode
	 *            数据模型
	 * @return
	 */
	public String readInternal(String fulltext, Mode mode);


	/**
	 * 内部，根据mode获取写文件数据
	 * 
	 * @param fulltext
	 *            文件正文
	 * @param mode
	 *            数据模型
	 * @return
	 */
	public String writeInternal(String fulltext, Mode mode);

}
