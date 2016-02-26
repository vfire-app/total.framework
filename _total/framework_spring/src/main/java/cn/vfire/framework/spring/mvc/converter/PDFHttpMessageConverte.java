package cn.vfire.framework.spring.mvc.converter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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

public class PDFHttpMessageConverte extends AbstractHttpMessageConverter<Object> {

	private static final Logger log = Logger.getLogger(PDFHttpMessageConverte.class);

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public static final String[]  DEFAULT_MEDIATYPE = {"application/json+pdf","application/xml+pdf","application/pdf"} ;

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	@Getter
	@Setter
	public  Charset charset = DEFAULT_CHARSET ;
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> mediaTypes = new LinkedList<MediaType>() ;
		MediaType temp = null ;
		for(String m : DEFAULT_MEDIATYPE){
			temp = MediaType.valueOf(m) ;
			mediaTypes.add(new MediaType(temp.getType(), temp.getSubtype(), this.charset)) ;
		}
		return Collections.unmodifiableList(mediaTypes);
	}

	@Override
	protected Long getContentLength(Object t, MediaType contentType) throws IOException {
		// TODO Auto-generated method stub
		return super.getContentLength(t, contentType);
	}
	
	
	

}
