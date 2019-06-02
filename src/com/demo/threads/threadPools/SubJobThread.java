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
		String threadName = "子进入线程" + index + ",父线程" + parentIndex; 
		System.out.println(threadName);
		try {
			int time = index * 5000;
			Thread.sleep(Long.valueOf(time));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束子线程" + index + ",父线程" + parentIndex);
	}

}
