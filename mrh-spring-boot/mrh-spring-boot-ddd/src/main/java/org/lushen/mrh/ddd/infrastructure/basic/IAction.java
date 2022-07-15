package org.lushen.mrh.ddd.infrastructure.basic;

/**
 * action 接口
 * 
 * @author hlm
 * @param <IN>
 * @param <OUT>
 */
public interface IAction<IN, OUT> {

	/**
	 * 执行 action
	 * 
	 * @param in
	 * @return
	 */
	public OUT execute(IN in);

	/**
	 * 无出参 action
	 * 
	 * @author hlm
	 *
	 */
	public static abstract class IVoidAction<IN> implements IAction<IN, Void> {

		@Override
		public Void execute(IN in) {
			execute0(in);
			return null;
		}

		protected abstract void execute0(IN in);

	}

}
