package com.zju.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS测试
 * @author LIN
 *
 *
 *	1 CAS是什么-----比较并交换CompareAndSwap
 */
public class CASDemo {
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		
		System.out.println(atomicInteger.compareAndSet(5, 10)+" 当前atomicInteger："+atomicInteger);
		System.out.println(atomicInteger.compareAndSet(5, 20)+" 当前atomicInteger："+atomicInteger);
		
		System.out.println(atomicInteger.getAndIncrement()+" 当前atomicInteger："+atomicInteger.get());
		System.out.println(atomicInteger.getAndIncrement()+" 当前atomicInteger："+atomicInteger.get());
		System.out.println(atomicInteger.getAndIncrement()+" 当前atomicInteger："+atomicInteger.get());
		System.out.println(atomicInteger.incrementAndGet()+" 当前atomicInteger："+atomicInteger.get());
		
		
	}
}
