package org.lushen.mrh.ddd.infrastructure.basic;

/**
 * 对象转换接口
 * 
 * @author hlm
 * @param <O>
 * @param <T>
 */
public interface IAssembler<T> {

	/**
	 * 转换为目标对象
	 * 
	 * @return
	 */
	public T apply();

	/**
	 * 自动处理 null 转换接口
	 * 
	 * @author hlm
	 * @param <O>
	 * @param <T>
	 */
	public abstract class INullAssembler<O, T> implements IAssembler<T> {

		protected O origin;

		public INullAssembler(O origin) {
			super();
			this.origin = origin;
		}

		@Override
		public T apply() {
			if(this.origin == null) {
				return null;
			} else {
				return doApply();
			}
		}

		protected abstract T doApply();

	}

}
