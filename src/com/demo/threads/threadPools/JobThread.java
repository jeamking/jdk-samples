package com.demo.threads.threadPools;

public class JobThread implements Runnable {
	private int index;
	private AbstractJob abstractJob;
	public JobThread(int index) {
		this.index = index;
	}

	@Override
	public void run() {
		String threadName = "进入线程" + index; 
		System.out.println(threadName);
		try {
			int time = index * 1000;
			try {
				Class c = Class.forName("com.demo.threads.threadPools.DeployPluginJob");
				try {
					abstractJob = (AbstractJob)c.newInstance();
					System.out.println(threadName + ",begin execute");
					abstractJob.execute(index);
					System.out.println(threadName + ",end execute");
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Thread.sleep(Long.valueOf(time));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("结束线程" + index);
	}

	public AbstractJob getAbstractJob() {
		return abstractJob;
	}
}
