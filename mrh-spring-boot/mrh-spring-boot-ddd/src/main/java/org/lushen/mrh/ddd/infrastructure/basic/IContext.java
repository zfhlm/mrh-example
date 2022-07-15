package org.lushen.mrh.ddd.infrastructure.basic;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 对象转换上下文
 * 
 * @author hlm
 * @param <T>
 */
public class IContext<T> {

	private T target;

	private IContext(T target) {
		super();
		this.target = target;
	}

	/**
	 * 配置目标对象
	 * 
	 * @param consumer
	 * @return
	 */
	public IContext<T> apply(Consumer<T> consumer) {
		consumer.accept(this.target);
		return this;
	}

	/**
	 * 获取目标对象
	 * 
	 * @return
	 */
	public T getTarget() {
		return this.target;
	}

	/**
	 * 创建目标对象上下文
	 * 
	 * @param supplier
	 * @return
	 */
	public static <T> IContext<T> create(Supplier<T> supplier) {
		return new IContext<T>(supplier.get());
	}

}
