package org.lushen.mrh.example.netty.protobuf.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@SuppressWarnings("unused")
public class TestExternalizable {

	public static void main(String[] args) throws Exception {

		long st = System.currentTimeMillis();

		for(int i=0 ;i<100000; i++) {

			MyTestBean bean = new MyTestBean(1, "zhangsan");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(bean);
			oos.flush();

			byte[] buf = bos.toByteArray();

			oos.close();
			bos.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(bis);

			MyTestBean bean2 = (MyTestBean) ois.readObject();

			ois.close();
			bis.close();

		}

		//813
		System.out.println(System.currentTimeMillis() - st);

	}

	public static class MyTestBean implements Externalizable {

		private int age;

		private String name;

		public MyTestBean() {
			super();
		}

		public MyTestBean(int age, String name) {
			super();
			this.age = age;
			this.name = name;
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			out.write(this.age);
			out.writeBytes(this.name);
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			this.age = in.readInt();
			this.name = in.readLine();
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TestBean [age=");
			builder.append(age);
			builder.append(", name=");
			builder.append(name);
			builder.append("]");
			return builder.toString();
		}

	}

}
