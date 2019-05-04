package com.zju.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS����
 * @author LIN
 *
 *
 *	1 CAS��ʲô-----�Ƚϲ�����CompareAndSwap
 */
public class CASDemo {
	public static void main(String[] args) {
		AtomicInteger atomicInteger = new AtomicInteger(5);
		
		System.out.println(atomicInteger.compareAndSet(5, 10)+" ��ǰatomicInteger��"+atomicInteger);
		System.out.println(atomicInteger.compareAndSet(5, 20)+" ��ǰatomicInteger��"+atomicInteger);
		
		System.out.println(atomicInteger.getAndIncrement()+" ��ǰatomicInteger��"+atomicInteger.get());
		System.out.println(atomicInteger.getAndIncrement()+" ��ǰatomicInteger��"+atomicInteger.get());
		System.out.println(atomicInteger.getAndIncrement()+" ��ǰatomicInteger��"+atomicInteger.get());
		System.out.println(atomicInteger.incrementAndGet()+" ��ǰatomicInteger��"+atomicInteger.get());
		
		
	}
}
