package cn.vfire.framework.spring.mvc.converter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GSONHttpMessageConverte extends AbstractHttpMessageConverter<Object> {

	private static final Logger log = Logger.getLogger(GSONHttpMessageConverte.class);

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public static final String  DEFAULT_MEDIATYPE = "text/gson" ;

	public static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private GsonBuilder gsonBuilder = new GsonBuilder();
	
	@Getter
	@Setter
	public  Charset charset = DEFAULT_CHARSET ;

	private List<MediaType> mediaTypes = new LinkedList<MediaType>() ;
	
	@Getter
	@Setter
	private String dateFormat = DEFAULT_DATEFORMAT;

	
	@Getter
	@Setter
	private boolean writeAcceptCharset = true;
	
	@Getter
	@Setter
	private boolean excludeFieldsWithoutExposeAnnotation = false;

	public GSONHttpMessageConverte() {
		this.setCharset(DEFAULT_CHARSET);
		this.setMediaTypes(DEFAULT_MEDIATYPE);
		this.setDateFormat(DEFAULT_DATEFORMAT);
		
		final String className = this.getClass().getName() ;
		final String mediaTypeInfo = DEFAULT_MEDIATYPE ;
		final String charset = this.charset.toString() ;
		
		log.debug(String.format("注册自定义%s数据解析器，解析MediaType类型为%s，默认处理字符集%s。", className, mediaTypeInfo,charset));
		log.debug(String.format("默认处理日期类型数据格式化%s。", DEFAULT_DATEFORMAT));
	}

	protected List<Charset> getAcceptedCharsets() {
		List<Charset> charsetList = new ArrayList<Charset>(1);
		charsetList.add(this.charset);
		return charsetList;
	}

	@Override
	protected Long getContentLength(Object t, MediaType contentType) throws IOException {
		Long contentLength = super.getContentLength(t, contentType);
		log.debug("进入自定义" + GSONHttpMessageConverte.class.getName() + "数据解析器，内容长度为" + contentLength + "。");
		return contentLength;
	}

	private Charset getContentTypeCharset(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			return contentType.getCharSet();
		} else {
			return this.charset;
		}
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Collections.unmodifiableList(this.mediaTypes);
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		Charset charset = this.getContentTypeCharset(inputMessage.getHeaders().getContentType());
		Object rs = StreamUtils.copyToString(inputMessage.getBody(), charset);

		log.debug(String.format("进入自定义%s数据解析器，处理字符集%s，读取数据流到到对象%s。", GSONHttpMessageConverte.class.getName(), String.valueOf(charset), String.valueOf(rs)));

		return rs;
	}
	
	public void setMediaTypes(List<String> mediaTypes) {
		if(mediaTypes!=null && mediaTypes.isEmpty()==false){
			for(String media : mediaTypes){
				this.setMediaTypes(media);
			}
		}
	}

	public void setMediaTypes(String mediaTypes) {
		if(this.mediaTypes.indexOf(mediaTypes)==-1){
			MediaType mt = MediaType.valueOf(mediaTypes);
			this.mediaTypes.add(new MediaType(mt.getType(),mt.getSubtype(),this.charset)) ;
		}
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	private String toJson(Object o) {

		try {

			if (this.excludeFieldsWithoutExposeAnnotation) {
				this.gsonBuilder = this.gsonBuilder.excludeFieldsWithoutExposeAnnotation();
			}

			Type genericType = TypeToken.get(o.getClass()).getType();

			Gson gson = gsonBuilder.setDateFormat(this.dateFormat).create();

			String json = gson.toJson(o, genericType);

			if (json == null || "".equals(json.trim())) {
				json = "{}";
			}

			return json;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}
	
	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(this.getAcceptedCharsets());
		}
		Charset charset = this.getContentTypeCharset(outputMessage.getHeaders().getContentType());
		StreamUtils.copy(this.toJson(o), charset, outputMessage.getBody());

		log.debug(String.format("进入自定义%s数据解析器，处理字符集%s，解析对象%s到Json。", GSONHttpMessageConverte.class.getName(), String.valueOf(charset), String.valueOf(o)));

	}

}
