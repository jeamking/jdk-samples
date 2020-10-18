package com.demo.threads.threadPools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DeployPluginJob extends AbstractJob {
	public static Map<Integer,List<Future<Void>>> futureListMap = new HashMap<Integer, List<Future<Void>>>();

	@Override
	public void execute(Integer normalJobId) {
		String logKey = "JobThreadMain.JobThread.DeployPluginJob.execute(),�߳�" + normalJobId;
		System.out.println(logKey + "����");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<Future<Void>> futureList = new ArrayList<Future<Void>>();
		for (int i=1; i<=5; i++) {
			SubJobThread jobThread = new SubJobThread(normalJobId, i);
			Future<Void> future = (Future<Void>) executor.submit(jobThread);
			futureList.add(future);
		}
		futureListMap.put(normalJobId, futureList);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();//û�е���shutdown������main�����������
		System.out.println(logKey + ",����,after shutdown");		
	}

	@Override
	public void destroy(Integer normalJobId) {
		List<Future<Void>> futureList = futureListMap.get(normalJobId);
		if (futureList != null) {
			String logKey = "JobThreadMain.JobThread.DeployPluginJob.destroy(),���߳�" + normalJobId + ",���߳�";
			for (int i=1; i<=futureList.size(); i++) {
				Future<Void> future = futureList.get(i-1);
				if (future.isDone()) {
					System.out.println(logKey + i + ",is ��done��:");
				} else {
					System.out.println(logKey + i + ",��cancel�� result:"+future.cancel(true));				
				}
			}			
		}
	}
}
