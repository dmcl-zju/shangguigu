package com.zju.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题以及原子引用
 * @author LIN
 *
 */

//----User类在AtomicReferenceDemo中定义了------

public class ABADemo {
		//普通原子引用
		private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
		//时间戳原子引用
		private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(100,1);
		
		//主函数测试
		public static void main(String[] args) {
			System.out.println("=============ABA问题的产生=============");
			new Thread(()->{
				atomicReference.compareAndSet(100, 101);
				atomicReference.compareAndSet(101, 100);
				
			},"线程1").start();
			
			new Thread(()->{
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(atomicReference.compareAndSet(100, 2019)+"当前值为："+atomicReference);
			},"线程2").start();
			
			//等待2s保证上面全部运行完
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=============ABA问题的解决=============");
			
			new Thread(()->{
				//int stamp = atomicStampedReference.getStamp();
				System.out.println(Thread.currentThread().getName()+"拿到的第1次版本号"+atomicStampedReference.getStamp());
				//延时1s保证线程4拿到同样初始值
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
				System.out.println(Thread.currentThread().getName()+"拿到的第2次版本号"+atomicStampedReference.getStamp());
				atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
				System.out.println(Thread.currentThread().getName()+"拿到的第3次版本号"+atomicStampedReference.getStamp());
				
			},"线程3").start(); 
			
			new Thread(()->{
				int stamp = atomicStampedReference.getStamp();
				System.out.println(Thread.currentThread().getName()+"拿到的第1次版本号"+stamp);
				//延时1s保证线程3执行了一次ABA
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean res = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
				System.out.println(Thread.currentThread().getName()+"拿到的第2次版本号"+atomicStampedReference.getStamp());
				System.out.println("修改结果"+res);
				
			},"线程4").start(); 
		
		}
}
