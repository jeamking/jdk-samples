package com.demo.threads;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callableʹ��ʾ��
 * @author Administrator
 *
 */
public class CallableDemo {

	public static void main(String[] args) {
		//start1CallableWithThread();
		//start1CallableWithExecutors();
		//start2CallableWithThread();
		start2CallableWithExecutors();
	}
	
	/**
	 * ͨ��Thread����1��callable
	 */
	private static void start1CallableWithThread() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        FutureTask<String> futureTask = new FutureTask<String>(deployCallable);
        System.out.println(getDateStr() + "main thread before start");
        new Thread(futureTask).start();
        //���߳����ߣ�����Ӱ��Callable���첽ִ��
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread after start");
        //�������̣߳�ֱ��Callable���в����ؽ��
        String res = null;
		try {
			res = futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask get:" + res);
        System.out.println(getDateStr() + "main thread main() end");		
	}
	
	/**
	 * ͨ��Thread����2��callable
	 */
	private static void start2CallableWithThread() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        FutureTask<String> futureTask1 = new FutureTask<String>(deployCallable);
        System.out.println(getDateStr() + "main thread before start");
        new Thread(futureTask1).start();
        //���߳����ߣ�����Ӱ��Callable���첽ִ��
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        FutureTask<String> futureTask2 = new FutureTask<String>(deployCallable);
        new Thread(futureTask2).start();
        
        System.out.println(getDateStr() + "main thread after start");
        //�������̣߳�ֱ��Callable���в����ؽ��
        String res1 = null;
		try {
			res1 = futureTask1.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask1 get:" + res1);
        //��ȡfuture2�Ľ��
        String res2 = null;
		try {
			res2 = futureTask2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask2 get:" + res2);        
        System.out.println(getDateStr() + "main thread main() end");		
	}	
	
	/**
	 * ͨ���̳߳�����1��callable
	 */
	private static void start1CallableWithExecutors() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        System.out.println(getDateStr() + "main thread before start");
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<String> futureTask = pool.submit(deployCallable);
        //���߳����ߣ�����Ӱ��Callable���첽ִ��
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread after start");
        //�������̣߳�ֱ��Callable���в����ؽ��
        String res = null;
		try {
			res = futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        System.out.println(getDateStr() + "main thread futureTask get:" + res);
        System.out.println(getDateStr() + "main thread main() end");
        pool.shutdown();		
	}	
	
	/**
	 * ͨ���̳߳�����2��callable
	 */
	private static void start2CallableWithExecutors() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        System.out.println(getDateStr() + "main thread before start");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> futureTask1 = pool.submit(deployCallable);
        //���߳����ߣ�����Ӱ��Callable���첽ִ��
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Future<String> futureTask2 = pool.submit(deployCallable);
        
        System.out.println(getDateStr() + "main thread after start");
        //�������̣߳�ֱ��Callable���в����ؽ��
        String res1 = null;
		try {
			res1 = futureTask1.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask1 get:" + res1);
        
        String res2 = null;
		try {
			res2 = futureTask2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask2 get:" + res2);        
        
        System.out.println(getDateStr() + "main thread main() end");
        pool.shutdown();		
	}	
	
	private static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime()) + " ";
	}

	class DeployCallable implements Callable<String>{
	    @Override
	    public String call() {
	        try {
	        	System.out.println(getDateStr() + Thread.currentThread().getName() + "-DeployCallable begin");
				Thread.sleep(5000);
				System.out.println(getDateStr() + Thread.currentThread().getName() + "-DeployCallable end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        return "deploy success";
	    }
	}
}
