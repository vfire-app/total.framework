package cn.vfire.common.util.text.mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.vfire.common.util.text.Mode;
import cn.vfire.common.util.text.ModeConverter;

public abstract class AbsModeConverter implements ModeConverter {

	
	private List<Type> type;


	public AbsModeConverter(List<Type> types) {
		this.type = types;
	}


	public AbsModeConverter(Type... types) {
		this(Arrays.asList(types));
	}


	public AbsModeConverter(Type type) {
		this(new Type[] { type });
	}


	public AbsModeConverter() {
		this(TextMode.class);
	}


	@Override
	public boolean support(File file, Mode mode) {
		return true;
	}


	@Override
	public List<Type> supportModes() {
		return Collections.unmodifiableList(type);
	}


	/**
	 * 计算文件大小
	 */
	@Override
	public long fileSize(File file, char unit) {
		try {
			double size = this.fileLength(file, unit);
			return new Double(size).longValue();
		} catch (FileNotFoundException e) {
			return -1;
		}
	}


	@Override
	public  boolean canRead(String fulltext, Mode mode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public  boolean canWrite(String fulltext, Mode mode) {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * 计算文件大小
	 * 
	 * @param file
	 * @param unit
	 *            B\K\M\G
	 * @return
	 * @throws FileNotFoundException
	 */
	public double fileLength(File file, char unit) throws FileNotFoundException {
		if (file != null && file.exists() && file.isFile()) {
			switch (unit) {
			case 'B':
				return new Long(file.length()).doubleValue();
			case 'K':
				return new Long(file.length()).doubleValue() / 1024.0;
			case 'M':
				return new Long(file.length()).doubleValue() / 1024.0 / 1024.0;
			case 'G':
				return new Long(file.length()).doubleValue() / 1024.0 / 1024.0 / 1024.0;
			default:
				return new Long(file.length()).doubleValue();
			}
		} else {
			throw new FileNotFoundException(String.format("File %s is not found.", file.getPath()));
		}
	}


	
	public void setType(List<Type> type) {
		this.type = type;
	}
	

}
