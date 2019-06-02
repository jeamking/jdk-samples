package com.demo.threads.threadPools;

public class SubJobThread implements Runnable {
	private int parentIndex;
	private int index;
	public SubJobThread(int parentIndex, int index) {
		this.index = index;
		this.parentIndex = parentIndex;
	}

	@Override
	public void run() {
		String threadName = "�ӽ����߳�" + index + ",���߳�" + parentIndex; 
		System.out.println(threadName);
		try {
			int time = index * 5000;
			Thread.sleep(Long.valueOf(time));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("�������߳�" + index + ",���߳�" + parentIndex);
	}

}
