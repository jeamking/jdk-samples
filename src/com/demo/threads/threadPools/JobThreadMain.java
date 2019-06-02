package com.demo.threads.threadPools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobThreadMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		String logKey = "JobThreadMain.main(),";
		System.out.println(logKey + "����");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<Future<Void>> futureList = new ArrayList<Future<Void>>();
		Map<Integer, JobThread> jobThreadMap = new HashMap<Integer, JobThread>();
		for (int i=1; i<=10; i++) {
			JobThread jobThread = new JobThread(i);
			Future<Void> future = (Future<Void>) executor.submit(jobThread);
			futureList.add(future);
			jobThreadMap.put(i, jobThread);
		}
		//System.out.println("��ȡ����ֵ: "+future.get());
		Thread.sleep(3000);
		for (int i=1; i<=futureList.size(); i++) {
			Future<Void> future = futureList.get(i-1);
			if (future.isDone()) {
				System.out.println(logKey + "��done��,�߳�" + i);
			} else {
				try {
					JobThread jobThread = jobThreadMap.get(i);
					AbstractJob abstractJob = jobThread.getAbstractJob();
					abstractJob.destroy(i);						
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(logKey + "��cancel��," +"�߳�" + i + ",result:"+future.cancel(true));
			}
		}
		System.out.println(logKey + "all ��cancel��");
		//executor.shutdown();//û�е���shutdown������main�����������
		System.out.println(logKey + "after shutdown");
	}

}
