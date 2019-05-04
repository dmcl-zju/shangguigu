package com.zju.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
	
	
	volatile int number = 0;
	public void addto60() {
		this.number = 60;
	}
	//��֤������ԭ����
	public /*synchronized*/ void addPlusPlus() {
		number++;
	}
	
	//ʹ��ԭ����
	AtomicInteger ai = new AtomicInteger();
	public void addAtomic() {
		ai.getAndIncrement();
	}
}


/**
 * 	1  ��֤volatile�Ŀɼ���
 * 		������֤��seeOkbyVolatile()
	2 volatile���߱�ԭ����
		������֤��main����
		mum++���߱�ԭ���ԣ���ײ������������裺��ȡ����һ��д�أ�����������һ�������̴߳�ϣ����ܻ����д���ǣ�����ʧд��ֵ
	�����
		1 ��number++������cynchronized----ȱ�㣺ɱ����ţ����Ӱ��Ч��
		2 ʹ��atomicInteger��֤ԭ����
	
 * @author LIN
 *
 */
public class VolatileDemo {
	public static void main(String[] args) {
		//��֤�ɼ���
		//seeOkbyVolatile();
		
		//��֤ԭ����
		MyData data = new MyData();
		for(int i=0;i<20;i++) {
			new Thread(()->{
				for(int j=0;j<1000;j++) {
					data.addPlusPlus();
					data.addAtomic();
				}
			},"�߳�"+i).start();
		}
		while(Thread.activeCount()>1) {
			Thread.yield();
		}
		System.out.println("20���߳�ִ�к�number="+data.number);
		System.out.println("20���߳�ִ�к�atomic="+data.ai);
		
	}
	
	
	
	
	public static void seeOkbyVolatile() {
		MyData data = new MyData();
		new Thread(() ->{
			System.out.println(Thread.currentThread().getName()+" ������");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			data.addto60();
			System.out.println(Thread.currentThread().getName()+" ������ֵΪ60");
		},"�߳�1").start();
		
		//�����߳��еȴ�
		while(data.number==0){
			
		}
		System.out.println(Thread.currentThread().getName()+" ִ�е���"+",number��ֵΪ��"+data.number);
	}
}
