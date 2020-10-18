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
		String logKey = "JobThreadMain.JobThread.DeployPluginJob.SubJobThread.run(),父线程" + parentIndex + ",子线程" + index; 
		System.out.println(logKey + "进入");
		try {
			int time = index * 5000;
			Thread.sleep(Long.valueOf(time));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(logKey + "结束");
	}

}
