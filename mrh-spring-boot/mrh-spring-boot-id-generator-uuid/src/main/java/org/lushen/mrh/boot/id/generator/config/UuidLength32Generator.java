package org.lushen.mrh.boot.id.generator.config;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
 * UUID 生成器
 * 
 * @author hlm
 */
@Component
public class UuidLength32Generator implements IdGenerator<String> {

	@Override
	public String generate() {
		char[] chars = new char[32];
		String uuid = UUID.randomUUID().toString();
		for(int i=0, off = 0; i<uuid.length(); i++) {
			char ch = uuid.charAt(i);
			if(ch != '-') {
				chars[off++] = ch;
			}
		}
		return new String(chars);
	}

}
