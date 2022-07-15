package org.lushen.mrh.ddd.infrastructure.basic;

/**
 * 主键ID生成器
 * 
 * @author hlm
 */
public interface IdGenerator {

	/**
	 * 生成主键ID
	 * 
	 * @return
	 */
	public Integer generate();

}
