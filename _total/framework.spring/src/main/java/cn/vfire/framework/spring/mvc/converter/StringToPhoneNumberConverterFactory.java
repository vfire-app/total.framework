package cn.vfire.framework.spring.mvc.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToPhoneNumberConverterFactory implements ConverterFactory<String, String> {
	@Override
	public <T extends String> Converter<String, T> getConverter(Class<T> targetType) {
		return null;
	}
}
