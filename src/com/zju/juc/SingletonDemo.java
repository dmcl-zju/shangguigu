package com.zju.juc;

/**
 * DCL����ģʽ����
 * @author LIN
 *
 */
public class SingletonDemo {
	
	//����volatile��ָֹ������
	private static volatile SingletonDemo instance = null;
	//˽�л����캯��
	private SingletonDemo(){
		System.out.println(Thread.currentThread().getName()+"�����˶���");
	}
	
	//DCL --double check lock ˫�˼�������
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
	//���Ժ���
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			new Thread(()->{
				SingletonDemo.getInstance();
			},"�߳�"+i).start();
		}
	}
}
