package org.lushen.mrh.boot.id.generator.define;

/**
 * ID 生成器
 * 
 * @author hlm
 * @param <R>
 */
public interface IdGenerator<R> {

	/**
	 * 生成 ID
	 * 
	 * @return
	 */
	public R generate();

}
