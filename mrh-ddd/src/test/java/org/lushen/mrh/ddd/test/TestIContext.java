package org.lushen.mrh.ddd.test;

import java.util.Random;
import java.util.UUID;

import org.lushen.mrh.ddd.infrastructure.basic.IContext;
import org.lushen.mrh.ddd.infrastructure.mybatis.model.TUser;

public class TestIContext {

	public static void main(String[] args) {

		println(IContext.create(TUser::new).apply(user -> {
			user.setUserId(new Random().nextInt(Integer.MAX_VALUE));
			user.setName(UUID.randomUUID().toString());
		}));

		println(IContext.create(TUser::new).apply(user -> {
			user.setUserId(new Random().nextInt(Integer.MAX_VALUE));
			user.setName(UUID.randomUUID().toString());
		}));

	}

	private static void println(IContext<TUser> IContext) {
		TUser target = IContext.getTarget();
		System.out.println(target.getUserId());
		System.out.println(target.getName());
	}

}
