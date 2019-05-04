package com.zju.juc;

/**
 * DCL单例模式测试
 * @author LIN
 *
 */
public class SingletonDemo {
	
	//增加volatile禁止指令重排
	private static volatile SingletonDemo instance = null;
	//私有化构造函数
	private SingletonDemo(){
		System.out.println(Thread.currentThread().getName()+"创建了对象");
	}
	
	//DCL --double check lock 双端检锁机制
	public static  SingletonDemo getInstance() {
		
		if(null==instance) {
			synchronized (SingletonDemo.class) {
				if(null==instance) {
					instance = new SingletonDemo();
				}
			}
		}
		return instance;
	}
	//测试函数
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			new Thread(()->{
				SingletonDemo.getInstance();
			},"线程"+i).start();
		}
	}
}
