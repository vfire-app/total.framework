package cn.vfire.common;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cn.vfire.common.util.text.TextUtil;
import cn.vfire.common.util.text.mode.TextMode;

public class TextUtilTest {

	
	@Test
	public void writeFile(){
		
		TextUtil textUtil = new TextUtil() ;
		
		File file = new File("/TextUtilTest/test.txt") ;
		
		TextMode mode = new TextMode() ;
		
		String bean = "你好" ;
		
		try {
			textUtil.writeFile(file, mode, bean);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(file.exists());
		
	}
	
	
	
}
