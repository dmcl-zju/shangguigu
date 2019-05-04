package com.zju.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
	
	
	volatile int number = 0;
	public void addto60() {
		this.number = 60;
	}
	//验证不满足原子性
	public /*synchronized*/ void addPlusPlus() {
		number++;
	}
	
	//使用原子类
	AtomicInteger ai = new AtomicInteger();
	public void addAtomic() {
		ai.getAndIncrement();
	}
}


/**
 * 	1  验证volatile的可见性
 * 		代码验证：seeOkbyVolatile()
	2 volatile不具备原子性
		代码验证：main函数
		mum++不具备原子性，其底层汇编有三个步骤：读取，加一，写回，如果在如果加一后被其他线程打断，可能会出现写覆盖，即丢失写的值
	解决：
		1 对number++操作加cynchronized----缺点：杀鸡用牛刀，影响效率
		2 使用atomicInteger保证原子性
	
 * @author LIN
 *
 */
public class VolatileDemo {
	public static void main(String[] args) {
		//验证可见性
		//seeOkbyVolatile();
		
		//验证原子性
		MyData data = new MyData();
		for(int i=0;i<20;i++) {
			new Thread(()->{
				for(int j=0;j<1000;j++) {
					data.addPlusPlus();
					data.addAtomic();
				}
			},"线程"+i).start();
		}
		while(Thread.activeCount()>1) {
			Thread.yield();
		}
		System.out.println("20个线程执行后，number="+data.number);
		System.out.println("20个线程执行后，atomic="+data.ai);
		
	}
	
	
	
	
	public static void seeOkbyVolatile() {
		MyData data = new MyData();
		new Thread(() ->{
			System.out.println(Thread.currentThread().getName()+" 启动了");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			data.addto60();
			System.out.println(Thread.currentThread().getName()+" 更新了值为60");
		},"线程1").start();
		
		//在主线程中等待
		while(data.number==0){
			
		}
		System.out.println(Thread.currentThread().getName()+" 执行到了"+",number的值为："+data.number);
	}
}
