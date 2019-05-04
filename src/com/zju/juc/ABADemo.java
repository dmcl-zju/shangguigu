package com.zju.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA�����Լ�ԭ������
 * @author LIN
 *
 */

//----User����AtomicReferenceDemo�ж�����------

public class ABADemo {
		//��ͨԭ������
		private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
		//ʱ���ԭ������
		private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(100,1);
		
		//����������
		public static void main(String[] args) {
			System.out.println("=============ABA����Ĳ���=============");
			new Thread(()->{
				atomicReference.compareAndSet(100, 101);
				atomicReference.compareAndSet(101, 100);
				
			},"�߳�1").start();
			
			new Thread(()->{
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(atomicReference.compareAndSet(100, 2019)+"��ǰֵΪ��"+atomicReference);
			},"�߳�2").start();
			
			//�ȴ�2s��֤����ȫ��������
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=============ABA����Ľ��=============");
			
			new Thread(()->{
				//int stamp = atomicStampedReference.getStamp();
				System.out.println(Thread.currentThread().getName()+"�õ��ĵ�1�ΰ汾��"+atomicStampedReference.getStamp());
				//��ʱ1s��֤�߳�4�õ�ͬ����ʼֵ
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
				System.out.println(Thread.currentThread().getName()+"�õ��ĵ�2�ΰ汾��"+atomicStampedReference.getStamp());
				atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
				System.out.println(Thread.currentThread().getName()+"�õ��ĵ�3�ΰ汾��"+atomicStampedReference.getStamp());
				
			},"�߳�3").start(); 
			
			new Thread(()->{
				int stamp = atomicStampedReference.getStamp();
				System.out.println(Thread.currentThread().getName()+"�õ��ĵ�1�ΰ汾��"+stamp);
				//��ʱ1s��֤�߳�3ִ����һ��ABA
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean res = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
				System.out.println(Thread.currentThread().getName()+"�õ��ĵ�2�ΰ汾��"+atomicStampedReference.getStamp());
				System.out.println("�޸Ľ��"+res);
				
			},"�߳�4").start(); 
		
		}
}
