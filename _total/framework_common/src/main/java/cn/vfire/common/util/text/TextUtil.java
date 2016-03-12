package cn.vfire.common.util.text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import cn.vfire.common.util.text.converter.JsonModeConverter;
import cn.vfire.common.util.text.converter.TextModeConverter;

/**
 * 读写文本文件工具类
 * 
 * @author ChenGang
 *
 */
public class TextUtil {

	private static final Logger log = Logger.getLogger(TextUtil.class);

	private static HashMap<String, ModeConverter> modeConverters = new HashMap<String, ModeConverter>();

	private static final ModeConverter DEFAULT_MODECONVERTER = new TextModeConverter();

	private Charset charsets = Charset.forName("UTF-8");


	static {
		modeConverters.put(TextModeConverter.class.toString(), new TextModeConverter());
		modeConverters.put(JsonModeConverter.class.toString(), new JsonModeConverter());
	}


	/**
	 * 读全文内容
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readFile(File file, Mode mode) throws IOException {

		ModeConverter mc = (mc = modeConverters.get(mode.getClass())) == null ? DEFAULT_MODECONVERTER : mc;

		if (mc.support(file, mode)) {

			if (mode.charset() != null) {
				this.setCharset(mode.charset());
			}

			String content = this.readFile(file);

			if (mc.canRead(content, mode)) {

				content = mc.readInternal(content, mode);

				return content;

			} else {

				log.info(String.format("解析器检测到文件%s的文本内容与给定的数据模型不相符，则直接读取全内容文件。解析器为%s，数据模型为%s。", file.getPath(),
						mc.getClass().toString(), mode.getClass().toString()));

			}

			return content;

		}

		log.info(String.format("不支持读取，发生在文件%s，解析器%s，解析模型%s。", file.getPath(), mc.getClass().toString(),
				mode.getClass().toString()));

		return null;
	}


	/**
	 * 写文件
	 * 
	 * @param file
	 * @param mode
	 * @param bean
	 * @throws IOException
	 */
	public <T> void writeFile(File file, Mode mode, T bean) throws IOException {

		ModeConverter mc = (mc = modeConverters.get(mode.getClass())) == null ? DEFAULT_MODECONVERTER : mc;

		if (mc.support(file, mode)) {

			if (mode.charset() != null) {
				this.setCharset(mode.charset());
			}

			String content = mode.transformContent(bean);

			if (mc.canWrite(content, mode)) {

				content = mc.writeInternal(content, mode);

			} else {

				log.info(String.format("解析器检测到文本内容与给定的数据模型不相符，则直接创建全内容文件%s。解析器为%s，数据模型为%s。", file.getPath(),
						mc.getClass().toString(), mode.getClass().toString()));

			}

			this.writeFile(file, content);

		}

		log.info(String.format("不支持写文件，发生在文件%s，解析器%s，解析模型%s。", file.getPath(), mc.getClass().toString(),
				mode.getClass().toString()));

	}


	/**
	 * 写文件
	 * 
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public void writeFile(File file, String content) throws IOException {
		this.setCharsets(this.charsets.toString());
		FileUtils.write(file, content, charsets);
	}


	/**
	 * 写文件
	 * 
	 * @param file
	 * @param content
	 * @param charsets
	 * @throws IOException
	 */
	public void writeFile(File file, String content, String charsets) throws IOException {
		this.setCharsets(charsets);
		FileUtils.write(file, content, charsets);
	}


	/**
	 * 直接读取文件，字符串形式返回文本内容。
	 * 
	 * @param file
	 *            被读取的文件
	 * @return
	 * @throws IOException
	 */
	public String readFile(File file) throws IOException {
		return this.readFile(file, this.charsets.toString());
	}


	/**
	 * 直接读取文件，字符串形式返回文本内容。
	 * 
	 * @param file
	 *            被读取的文件
	 * @param charsets
	 *            指定字符集
	 * @return
	 * @throws IOException
	 */
	public String readFile(File file, String charsets) throws IOException {
		this.setCharsets(charsets);
		String fullText = FileUtils.readFileToString(file, this.charsets);
		return fullText;
	}


	/**
	 * 设置解析器
	 * 
	 * @param modeConverters
	 */
	public void setModeConverters(List<ModeConverter> modeConverterList) {
		HashMap<String, ModeConverter> mcMap = new HashMap<String, ModeConverter>();
		for (ModeConverter mc : modeConverterList) {
			for (Type type : mc.supportModes()) {
				mcMap.put(type.toString(), mc);
			}
		}
		modeConverters = (HashMap<String, ModeConverter>) Collections.unmodifiableMap(mcMap);
	}


	/**
	 * 设置解析器
	 * 
	 * @param modeConverterList
	 */
	public void setModeConverters(ModeConverter modeConverterList) {
		HashMap<String, ModeConverter> mcMap = new HashMap<String, ModeConverter>();
		for (Type type : modeConverterList.supportModes()) {
			mcMap.put(type.toString(), modeConverterList);
		}
		modeConverters = (HashMap<String, ModeConverter>) Collections.unmodifiableMap(mcMap);
	}


	/**
	 * 设置文件操作字符集
	 * 
	 * @param charset
	 */
	public void setCharsets(String charset) {
		this.charsets = Charset.forName(charset);
	}


	/**
	 * 设置文件操作字符集
	 * 
	 * @param charset
	 */
	public void setCharset(Charset charset) {
		this.charsets = charset;
	}

}
