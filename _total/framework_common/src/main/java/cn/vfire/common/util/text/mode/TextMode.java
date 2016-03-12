package cn.vfire.common.util.text.mode;

import java.nio.charset.Charset;

import cn.vfire.common.util.text.Mode;

public class TextMode implements Mode {

	private Charset charset;


	@Override
	public String transformContent(Object bean) {

		if (bean == null) { return ""; }

		return bean.toString();
	}


	@Override
	public Charset charset() {
		return this.charset;
	}


	public void setCharset(Charset charset) {
		this.charset = charset;
	}


	public void setCharset(String charset) {
		this.charset = Charset.forName(charset);
	}

}
