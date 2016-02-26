package cn.vfire.frameword.common.util.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * 对properties属性文件做读写操作工具。
 * 
 * @author ChenGang
 *
 */
public class PropertiesUtil {

	private static PropertiesUtil Prop;

	private Map<String, String> SessionCache = new ConcurrentHashMap<String, String>();

	private static String DefaultProperties = "appconfig.properties";

	public static final PropertiesUtil getSingleton() throws IOException {
		if (Prop == null) {
			Prop = new PropertiesUtil(null, null);
		}
		Prop.reload();
		return Prop;
	}

	public static final PropertiesUtil getSingleton(String... properties) throws IOException {
		if (Prop == null) {
			Prop = new PropertiesUtil(properties, null);
		}
		Prop.reload();
		return Prop;
	}

	private static boolean isFindRegexCase(String input, String regex) {
		Matcher matcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input);
		return matcher.find();
	}

	@Getter
	@Setter
	private String encoding = "UTF-8";

	@Getter
	private String[] properties;

	public void setProperties(String... properties) {
		this.properties = properties;
	}

	/**
	 * 创建一个读取多位置的配置文件，并指定字符集编码，默认为UTF-8。<br />
	 * 对指定配置文件位置，支持如下格式：<br />
	 * <ul>
	 * <li>classpath:appconfig.properties</li>
	 * <li>classpath:config/appconfig.properties</li>
	 * <li>classpath:config/app-*-config.properties</li>
	 * <li>app_properties.properties</li>
	 * <li>/config/app_properties.properties</li>
	 * <li>D:\workspace\classes\app_properties.properties</li>
	 * </ul>
	 * 
	 * @param properties
	 * @param encoding
	 * @throws IOException
	 */
	public PropertiesUtil(String[] properties, String encoding) {

		this.properties = properties == null ? new String[] { DefaultProperties } : properties;

		this.encoding = encoding == null ? this.encoding : encoding;

	}

	/**
	 * 如果内部发生异常则返回null。
	 * 
	 * @param path
	 * @return
	 */
	private String[] classPathHandle(String path) {

		String _path = path == null || "".equals(path) ? "/" : path;
		String _regex = "^classpath:";
		if (isFindRegexCase(path, _regex)) {
			_path = _path.replaceFirst(_regex, "/");
		}

		try {

			_path = _path.replaceAll("/+", "/");
			_path = PropertiesUtil.class.getResource(_path).getPath();

			File file = new File(_path);

			List<String> filelist = new ArrayList<String>();

			if (file.isDirectory()) {

				filelist = Arrays.asList(file.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						boolean flag = isFindRegexCase(name, "\\.properties$");
						return flag;
					}
				}));

				for (String name : filelist) {
					name = String.format("%s/%s", path, name).replaceAll("/+", "/");
				}

				return filelist.toArray(new String[filelist.size()]);
			}

			return new String[] { path };

		} catch (Exception e) {

			return null;

		}

	}

	/**
	 * 属性值引用赋值
	 * 
	 * @param val
	 * @return
	 */
	private String[] extractionValue(String val) {

		if (val == null || "".equals(val))
			return null;

		Matcher matcher = Pattern.compile("\\%[^\\%]+\\%").matcher(val);

		List<String> strlist = new ArrayList<String>(2);

		while (matcher.find()) {
			strlist.add(matcher.group().replaceAll("%", ""));
		}

		int len = strlist.size();

		return len > 0 ? strlist.toArray(new String[len]) : null;
	}

	/**
	 * 当入参的Path执行的既不是文件也不是目录的时候返回null。
	 * 
	 * @param path
	 * @return
	 */
	private File[] filePathHandle(String path) {

		List<File> filelist = new ArrayList<File>();

		File file = new File(path);

		if (file.isFile()) {
			boolean flag = isFindRegexCase(file.getName(), "\\.properties$");
			if (flag) {
				return new File[] { file };
			}
		}

		if (file.isDirectory()) {
			filelist = Arrays.asList(file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					boolean flag = isFindRegexCase(name, "\\.properties$");
					return flag;
				}
			}));

			return filelist.toArray(new File[filelist.size()]);
		}

		return null;

	}

	/**
	 * 通过属性名称获取对应的值
	 * 
	 * @param name
	 * @return
	 */
	public String get(String name) {
		return SessionCache.get(name);
	}

	/**
	 * 通过属性名称获取对应的值，当属性对应的值为null或者不存在该属性的时候，返回给出的默认值defaultValue。
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String get(String name, String defaultValue) {
		String value = SessionCache.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 通过属性key的前缀，获取前缀匹配的值的集合。集合中属性name以属性key前缀后面的部分。 例如：
	 * 
	 * <pre>
	 * 属性如下
	 * Mangager.Login.5.ID=5
	 * Mangager.Login.5.UserName=admin
	 * Mangager.Login.5.Password=password
	 * 
	 * key的前缀获取map集合
	 * Map map = getMap("Mangager.Login");
	 * 
	 * 结果map结合为：{5.ID=5,5.UserName=admin,5.Password=password}
	 * </pre>
	 * 
	 * @param keyPrefix
	 * @return 永远返回map对象，不会返回null。
	 */
	public Map<String, String> getMap(String keyPrefix) {

		Map<String, String> map = new LinkedHashMap<String, String>();

		if (SessionCache.containsKey(keyPrefix)) {
			map.put(keyPrefix, SessionCache.get(keyPrefix));
			return map;
		}

		String regex = String.format("^(%s)(\\.[^\\.\\=]+)", keyPrefix);

		Pattern pattern = Pattern.compile(regex);

		for (String k : SessionCache.keySet()) {

			if (k == null) {
				continue;
			}

			Matcher matcher = pattern.matcher(k);

			if (matcher.find()) {

				String name = k.replace(keyPrefix + ".", "");

				map.put(name, SessionCache.get(k));

			}
		}

		return map;
	}

	/**
	 * 直接获取Bean。与getMap()方法相似。
	 * 
	 * @param keyPrefix
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T getBean(String keyPrefix, Class<T> cls) throws Exception {

		Map<String, String> map = this.getMap(keyPrefix);

		T bean = cls.newInstance();

		Field[] fields = bean.getClass().getDeclaredFields();

		if (map.isEmpty() == false && fields != null) {

			for (Field f : fields) {

				f.setAccessible(true);

				String fieldName = f.getName();

				Class<?> type = f.getType();

				if (map.containsKey(fieldName)) {

					f.set(bean, this.toValueForBaseType(map.get(fieldName), type));
				}
			}

			return bean;

		}

		return null;
	}

	private Object toValueForBaseType(String value, Class<?> type) {

		if (value == null) {
			return null;
		}

		int len = value.length();

		if (byte.class == type) {

			return Byte.parseByte(value);

		} else if (short.class == type) {

			return Short.parseShort(value);

		} else if (int.class == type) {

			return Integer.parseInt(value);

		} else if (long.class == type) {

			return Long.parseLong(value);

		} else if (float.class == type) {

			return Float.parseFloat(value);

		} else if (double.class == type) {

			return Double.parseDouble(value);

		} else if (char.class == type) {

			return len == 1 ? value.charAt(0) : -1;

		} else if (boolean.class == type) {

			return Boolean.parseBoolean(value);

		} else if (Byte.class == type) {

			return Byte.valueOf(value);

		} else if (Short.class == type) {

			return Short.valueOf(value);

		} else if (Integer.class == type) {

			return Integer.valueOf(value);

		} else if (Long.class == type) {

			return Long.valueOf(value);

		} else if (Float.class == type) {

			return Float.valueOf(value);

		} else if (Double.class == type) {

			return Double.valueOf(value);

		} else if (Character.class == type) {

			return len == 1 ? value.charAt(0) : null;

		} else if (Boolean.class == type) {

			if ("true".equalsIgnoreCase(value)) {
				return Boolean.valueOf(true);
			}

			if ("false".equalsIgnoreCase(value)) {
				return Boolean.valueOf(true);
			}

			return null;

		}

		return null;
	}

	/**
	 * 读取属性文件流解析到sessioncache中，增量覆盖模式。
	 * 
	 * @param in
	 * @param encoding
	 * @throws IOException
	 */
	private void load(InputStream in, String encoding) throws IOException {

		Properties prop = new Properties();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));

		prop.load(reader);

		Iterator<String> it = prop.stringPropertyNames().iterator();

		while (it.hasNext()) {

			String key = it.next();
			String val = prop.getProperty(key, null);

			String[] exts = this.extractionValue(val);

			if (exts != null) {
				for (int i = 0; i < exts.length; i++) {
					String v = prop.getProperty(exts[i], System.getProperty(exts[i], System.getenv(exts[i])));
					if (v != null) {
						SessionCache.put(key, val.replaceAll("%" + exts[i] + "%", v));
					}
				}
				continue;
			}
			SessionCache.put(key, val);
		}

		in.close();
		reader.close();

	}

	/**
	 * 加载给定物理位置存在的File
	 * 
	 * @param properties
	 * @throws IOException
	 */
	private void loadFile(File properties) throws IOException {

		FileInputStream in = null;

		try {
			in = new FileInputStream(properties);
		} catch (IOException e) {
			throw new IOException("加载properties文件失败，请检查是否真实存在该文件。", e);
		}

		this.load(in, this.encoding);

	}

	/**
	 * 加载classpath路径下相对位置的属性文件。
	 * 
	 * @param properties
	 * @throws IOException
	 */

	private void loadProperties(String properties) throws IOException {

		InputStream in = null;

		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(properties);
		} catch (Exception e) {
			throw new IOException("加载properties文件失败，请检查是否真实存在该文件。", e);
		}

		this.load(in, this.encoding);

	}

	/**
	 * 重新加载properties属性文件
	 * 
	 * @throws IOException
	 */
	public void reload() throws IOException {
		for (String pro : this.properties) {

			{
				String[] files = null;
				if ((files = this.classPathHandle(pro)) != null) {
					for (String file : files) {
						this.loadProperties(file);
					}
					return;
				}
			}

			{
				File[] files = null;
				if ((files = this.filePathHandle(pro)) != null) {
					for (File file : files) {
						this.loadFile(file);
					}
					return;
				}
			}

		}
	}

}
