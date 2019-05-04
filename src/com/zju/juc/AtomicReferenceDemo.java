package com.zju.juc;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题以及原子引用
 * @author LIN
 *
 */

class User{
	int age;
	String name;
	public User(int age,String name) {
		this.age = age;
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + "]";
	}
	
}
public class AtomicReferenceDemo {

		public static void main(String[] args) {
			User z3 = new User(10,"z3");
			User li4 = new User(20,"li4");
			
			AtomicReference<User> atomicReference = new AtomicReference<User>();
			atomicReference.set(z3);
			System.out.println(atomicReference.compareAndSet(z3, li4)+"  当前对象为："+atomicReference.get().toString());
			System.out.println(atomicReference.compareAndSet(z3, li4)+"  当前对象为："+atomicReference.get().toString());
		}
}
